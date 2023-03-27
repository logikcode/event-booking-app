/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.reference.excel;

import com.bw.reference.excel.ExcelDataExtractor;
import org.apache.poi.ss.usermodel.Row;

/**
 * @param <E>
 * @author Neme Iloeje niloeje@byteworks.com.ng
 */
public interface RowResultEventHandler<E> {

    boolean onRowResult(int index, Row row, ExcelDataExtractor.RowResult<E> result);
}
