import * as React from "react";
import { Box } from "@mui/material";
import FileUploadIcon from "@mui/icons-material/FileUpload";

export default function FileComponent({ onChange }) {
  return (
    <Box sx={{ width: "100%", height: "10rem", border: "1px dashed #aaa" }}>
      <label htmlFor="file">
        <input type="file" id="file" hidden onChange={onChange}/>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            width: "100%",
            height: "100%",
          }}
        >
          <Box sx={{ marginY: "auto" }}>Drag and drop image</Box>
          <Box>
            <FileUploadIcon/>
          </Box>
        </Box>
      </label>
    </Box>
  );
}
