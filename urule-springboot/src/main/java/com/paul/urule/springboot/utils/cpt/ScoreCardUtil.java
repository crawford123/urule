package com.paul.urule.springboot.utils.cpt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.bstek.urule.model.scorecard.ScorecardDefinition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright © 2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2019-12-16 10:04
 * @Version
 */

public class ScoreCardUtil {


    /**
     * 简化评分卡报文
     *
     * @param scorecardDefinition
     * @return Map<String,Object>
     */
    public static Map<String,Object> simplifyScoreCardContent(ScorecardDefinition scorecardDefinition) {
        List<Object> list = new ArrayList<>();
        Map<String,Object> scoreCardContentMap = new HashMap<>();
        String scoringType = null;
        //简化cells报文，使cells报文为二维数组形式
        //解析报文
        Map<String, Object> resultMap = BeanUtil.beanToMap(scorecardDefinition);
        if(MapUtil.isNotEmpty(resultMap)) {
            List<Object> cellsList = (List<Object>) resultMap.get("cells");
            scoringType = MapUtil.getStr(resultMap, "scoringType");
            Map<String, List<Map<String, Object>>> rows = new LinkedHashMap<>();
            if (CollUtil.isNotEmpty(cellsList)) {
                for (Object cardCell : cellsList) {
                    Map<String, Object> dataMap = BeanUtil.beanToMap(cardCell);
                    String row = String.valueOf(MapUtil.getInt(dataMap, "row"));
                    if (!rows.containsKey(row)) {
                        rows.put(row, new ArrayList<>());
                    }
                    rows.get(row).add(dataMap);
                }
            }
            //组装报文
            Iterator<Map.Entry<String, List<Map<String, Object>>>> iterator = rows.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Map<String, Object>>> entry = iterator.next();
                list.add(entry.getValue());
            }
            List<Object> rowsList = (List<Object>)resultMap.get("rows");
            // cell.put("rows", rowsList);
            //给评分卡每个单元格设定id属性
            String id = null;
            //改变评分卡单元格的行数和列数
            for (Object object : list) {
                List<Map<String, Object>> cardCellList = (List<Map<String, Object>>) object;
                for (Map<String, Object> cardCell : cardCellList) {
                    //加上defaultValue属性，id属性和delete属性
                    String type = MapUtil.getStr(cardCell, "type");
                    if ("attribute".equals(type)) {
                        //VariableUtil.parseVariableLabel(cardCell);
                        id = IdUtil.fastSimpleUUID();
                    }
                    cardCell.put("id", id);
                    //覆盖每个CardCell的col属性和row属性
                    int col = MapUtil.getInt(cardCell, "col");
                    int row = MapUtil.getInt(cardCell, "row");
                    cardCell.put("col", col - 1);
                    cardCell.put("row", row - 2);
                    if (CollUtil.isNotEmpty(rowsList)) {
                        List<Map<String, Object>> attributeRowList = new ArrayList<>();
                        rowsList.forEach(attributeRow -> {
                            attributeRowList.add(BeanUtil.beanToMap(attributeRow));
                        });
                        //判断该单元格是否为属性
                        if (col == 1) {
                            //进行过滤
                            List<Map<String, Object>> filteredRowsList = attributeRowList.stream().filter(attributeRow -> row == MapUtil.getInt(attributeRow, "rowNumber")).collect(Collectors.toList());
                            if(CollUtil.isNotEmpty(filteredRowsList)){
                                filteredRowsList.forEach(attributeRow -> {
                                    List<Object> conditionRows = (List<Object>) attributeRow.get("conditionRows");
                                    if (CollUtil.isNotEmpty(conditionRows)) {
                                        cardCell.put("rowSpan", conditionRows.size() + 1);
                                    } else {
                                        cardCell.put("rowSpan", 1);
                                    }
                                });
                            }
                            //设定delete属性
                        } else if (col == 2) {
                            //进行过滤
                            List<Object> filteredRowsList = attributeRowList.stream().filter(attributeRow -> row == MapUtil.getInt(attributeRow, "rowNumber")).collect(Collectors.toList());
                            if (CollUtil.isNotEmpty(filteredRowsList)) {
                                cardCell.put("delete", null);
                            } else {
                                cardCell.put("delete", "delete");
                            }
                        }
                    }
                }
            }
        }
        scoreCardContentMap.put("scoringType", scoringType);
        scoreCardContentMap.put("cells",list);
        return scoreCardContentMap;
    }
}
