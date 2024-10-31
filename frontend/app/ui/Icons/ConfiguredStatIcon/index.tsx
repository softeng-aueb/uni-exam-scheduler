import * as React from "react";

import CheckBoxOutlineBlankIcon from "@mui/icons-material/CheckBoxOutlineBlank";
import CheckBoxOutlinedIcon from "@mui/icons-material/CheckBoxOutlined";

export default function ConfiguredStatIcon({ isConfigured }: { isConfigured: any }) {
  return (
    isConfigured ?
      (<CheckBoxOutlinedIcon sx={{ color: "#078D95", fontSize: "2.5rem" }}/>) :
      (<CheckBoxOutlineBlankIcon sx={{ color: "#078D95", fontSize: "2.5rem" }}/>)
  );
}
