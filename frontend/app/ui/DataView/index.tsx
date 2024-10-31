import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableRow from "@mui/material/TableRow";

export type DataViewProps = {
  data?: any; // TODO
  outerBorders?: boolean
  innerBorders?: boolean
  compact?: boolean
  right?: boolean
}

export default function DataView({ data, outerBorders, innerBorders, compact, right }: DataViewProps) {
  const sxTableContainer = {
    borderColor: "#ddd",
    borderRadius: "8px",
    borderStyle: "solid",
    borderWidth: (outerBorders ? 1 : 0),
  };

  const sxTable = {
    width: "100%",
    margin: 0,
  };

  const sxInnerBorders1 = {
    "&:last-child td, &:last-child th": {
      border: 0,
    },
  };

  const sxInnerBorders2 = {
    "& td, & th": {
      border: 0,
    },
    "&:last-child td, &:last-child th": {
      border: 0,
    },
  };

  const sxTableCellL = {
    fontWeight: 600,
    padding: compact ? "6px 12px" : "12px",
    textAlign: "right",
    whiteSpace: "nowrap",
  };

  const sxTableCellR = {
    padding: compact ? "6px 12px" : "12px",
    textAlign: right ? "right" : "left",
    width: "100%",
  };

  return (
    <TableContainer style={sxTableContainer}>
      <Table sx={sxTable}>
        <TableBody>
          {data && data.map((row, index) => {
            return row && <TableRow key={`row-${index}`} sx={innerBorders ? sxInnerBorders1 : sxInnerBorders2}>
              <TableCell sx={sxTableCellL}>{row?.key}:</TableCell>
              <TableCell sx={sxTableCellR}>{row?.val}</TableCell>
            </TableRow>;
          })}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
