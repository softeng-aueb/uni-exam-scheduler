import React from "react";

// MUI
import { Box, Grid, Tooltip } from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";

// Interface
import { IGroupingBox } from "../../lib/dbOperations/schemas/ComponentsInterfaces";

export default function GroupingBox(props: IGroupingBox) {
  const {
    title,
    theme,
    marginBottom,
    marginTop,
    paddingBottom,
    paddingLeft,
    paddingRight,
    paddingTop,
    children,
    info,
  } = props;

  const renderedTitle = <Box sx={{ fontWeight: "bold", marginBottom: 1 }}>{title}</Box>;

  const renderedInfo = (
    <Tooltip title={info}>
      <InfoIcon/>
    </Tooltip>
  );

  return (
    <Box sx={{
      backgroundColor: theme === "dark" ? "#ffffff" : "#fafafa",
      border: "1px solid #dddddd",
      borderRadius: 1,
      marginBottom: marginBottom ?? "1rem",
      marginTop: marginTop ? "1rem" : 0,
      paddingBottom: paddingBottom ?? "1rem",
      paddingLeft: paddingLeft ?? "1rem",
      paddingRight: paddingRight ?? "1rem",
      paddingTop: paddingTop ?? "1rem",
    }}>
      <Grid container spacing={2} justifyContent={"left"} alignItems={"left"}>
        <Grid item xs={11}>{title && renderedTitle}</Grid>
        <Grid item xs={1}>{info && renderedInfo}</Grid>
      </Grid>
      {children}
    </Box>
  );
};
