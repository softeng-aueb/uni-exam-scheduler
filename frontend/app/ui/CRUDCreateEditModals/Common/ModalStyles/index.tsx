import React from "react";

// MUI
import CloseIcon from "@mui/icons-material/Close";
import { Box, Modal } from "@mui/material";

const styleModalWrapper = {
  display: "flex",
  margin: "32px",
  overflow: "auto",
};
const styleModalWrapperInner = {
  alignItems: "center",
  background: "#fff",
  border: "1px solid #dddddd",
  borderRadius: "10px",
  boxShadow: 24,
  display: "flex",
  justifyContent: "center",
  margin: "auto",
  maxWidth: 1000,
  minWidth: 350,
  position: "relative",
  width: 1000,
  zIndex: 0,
};
const styleModalBox = {
  position: "relative",
  width: "100%",
  maxWidth: "100%",
};
const styleModalBoxHeader = {
  background: "#f5f5f5",
  borderBottom: "1px solid #e0e0e0",
  borderTopLeftRadius: "10px",
  borderTopRightRadius: "10px",
  display: "flex",
  justifyContent: "flex-end",
  padding: "1rem 1rem 1rem 2rem",
  // width: "100%",
};
const styleModalBoxHeaderText = {
  flexGrow: 1,
  fontSize: "1.2em",
  fontWeight: "bold",
};
const styleModalBoxHeaderIcon = {
  paddingTop: "2px",
};
const styleModalBoxContent = {
  marginBottom: "1rem",
  marginTop: "1rem",
  overflowX: "auto",
  padding: "1rem 2rem",
};

export default function CreateEditModalCommon(props: any) {
  const { open, handleClose, children, headerTitle } = props;

  return (
    <Modal
      open={open}
      onClose={handleClose}
      sx={styleModalWrapper}
      aria-labelledby="common-modal"
      aria-describedby="common-modal"
    >
      <Box sx={styleModalWrapperInner}>
        <Box sx={styleModalBox}>
          <Box sx={styleModalBoxHeader}>
            <Box sx={styleModalBoxHeaderText}>
              {headerTitle}
            </Box>
            <Box sx={styleModalBoxHeaderIcon}>
              <CloseIcon sx={{ cursor: "pointer" }} onClick={() => handleClose()}/>
            </Box>
          </Box>
          <Box sx={styleModalBoxContent}>
            {children}
          </Box>
        </Box>
      </Box>
    </Modal>
  );
}
