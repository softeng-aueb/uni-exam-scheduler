import * as React from "react";

import { Box } from "@mui/material";
import {
  GridToolbarColumnsButton,
  GridToolbarContainer,
  GridToolbarDensitySelector,
  GridToolbarExport,
  GridToolbarFilterButton,
} from "@mui/x-data-grid-pro";

type Props = {
  fileName?: string;
}
export default function DataGridToolbarComponent(props: Props) {
  const { fileName } = props;
  return (
    <Box sx={{ borderBottom: "1px solid #e0e0e0", display: "flex", justifyContent: "flex-end" }}>
      <GridToolbarContainer>
        <GridToolbarColumnsButton/>
        <GridToolbarFilterButton/>
        <GridToolbarDensitySelector/>
        <GridToolbarExport
          // printOptions={{ disableToolbarButton: true }}
          csvOptions={{ allColumns: true, fileName: fileName || "Download" }}
        />
      </GridToolbarContainer>
    </Box>
  );
}
