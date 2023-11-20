package com.me.mseotsanyana.mande.application.repository.preference;

public interface iUploadGlobalRepository {
    boolean deleteFrequencies();
    boolean deletePeriods();
    boolean deleteFiscalYears();
    boolean deleteCharts();

    boolean addFrequencyFromExcel();
    boolean addFiscalYearFromExcel();
    boolean addChartFromExcel();
}
