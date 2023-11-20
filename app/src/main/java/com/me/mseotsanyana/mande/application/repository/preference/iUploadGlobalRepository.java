package com.me.mseotsanyana.mande.application.preference;

public interface iUploadGlobalRepository {
    boolean deleteFrequencies();
    boolean deletePeriods();
    boolean deleteFiscalYears();
    boolean deleteCharts();

    boolean addFrequencyFromExcel();
    boolean addFiscalYearFromExcel();
    boolean addChartFromExcel();
}
