import React from "react";
import { DataGridPro as MuiDataGridPro } from "@mui/x-data-grid-pro";
import Card from "@mui/material/Card";

import DataGridToolbarComponent from "./DataGridToolbar";
import SpinnerWithPromise from "../Spinner/SpinnerPromise";

interface Props {
  rows: any;
  columns: any;
  spinnerArea?: any;
  customToolbar?: any;
  onCellEditCommit?: any;
  onColumnVisibilityModelChange?: any;
  rowHeightAuto?: boolean;
  fileName?: string;
}

export type { GridColDef, GridRowsProp, } from "@mui/x-data-grid-pro";

export {
  GridToolbarColumnsButton,
  GridToolbarContainer,
  GridToolbarDensitySelector,
  GridToolbarExport,
  GridToolbarFilterButton,
} from "@mui/x-data-grid-pro";

export function DataGridComponent({
                                    rows,
                                    columns,
                                    spinnerArea,
                                    customToolbar,
                                    onColumnVisibilityModelChange,
                                    rowHeightAuto = false,
                                    fileName,
                                  }: Props) {
  return (
    <Card sx={{ width: "100%", marginBottom: 4 }}>
      {!rows?.length && spinnerArea && (
        <SpinnerWithPromise area={spinnerArea}/>
      )}
      {/* {rows?.length > 0 && ( */}
      <MuiDataGridPro
        rows={rows || []}
        columns={columns}
        getRowHeight={() => rowHeightAuto ? "auto" : null}
        getRowId={(row) => row._id ? row._id : row.id ? row.id : row.uuid ? row.uuid : row.uid}
        // pageSize={pageSize}
        disableVirtualization
        onColumnVisibilityModelChange={onColumnVisibilityModelChange}
        // rowsPerPageOptions={[10, 25, 50]}
        // onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
        // disableSelectionOnClick
        // onCellEditCommit={onCellEditCommit}
        slots={{
          toolbar: customToolbar ? customToolbar : (props) => <DataGridToolbarComponent {...props} fileName={fileName}/>
        }}
        initialState={{ pinnedColumns: { right: ["actions"] } }}
      />
      {/* )} */}
    </Card>
  );
}
