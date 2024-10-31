import React from "react";

// MUI
import CloseIcon from "@mui/icons-material/Close";
import { Box, Modal } from "@mui/material";

import ViewJson from "../ViewJson";

interface Props {
  open: boolean
  handleClose: any
  rowData: any
}

const style = {
  position: "absolute",
  top: "40%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 800,
  bgcolor: "background.paper",
  borderRadius: "8px",
  boxShadow: 24,
  p: 3,
};

export default function RowDetailModal({
  open,
  handleClose,
  rowData,
}: Props) {
  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="dialog-modal"
      aria-describedby="dialog-modal"
    >
      <Box sx={style}>
        {/* Header */}
        <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
          <CloseIcon sx={{ cursor: "pointer" }} onClick={() => handleClose()}/>
        </Box>
        {/* Content */}
        <ViewJson data={rowData} collapsed={false}/>
      </Box>
    </Modal>
  );
}
