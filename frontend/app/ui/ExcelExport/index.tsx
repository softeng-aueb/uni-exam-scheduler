import React from "react";
import { CSVDownload } from "react-csv";

export const ExportCSV = ({ data }) => {
  return (

    <CSVDownload data={data} target="_blank"/>

  );
};
