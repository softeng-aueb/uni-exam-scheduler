import React, { useState } from "react";
import NextLink from "next/link";

import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import IconButton from "@mui/material/IconButton";
import UndoIcon from "@mui/icons-material/Undo";

export type WarningAlertProps = {
  uri: string,
  title?: string,
  message?: string,
  shouldWarn?: boolean,
  returnCb?: Function, // Cb should be invoked once the user had aggreed to return/close window
};

type Props = { elementId: string, returnProps: WarningAlertProps };

export default function ReturnButton({
  elementId = "",
  returnProps,
}: Props) {
  const [open, setOpen] = useState(false);

  const shouldWarn = "shouldWarn" in returnProps && returnProps.shouldWarn === true;

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (aggredToClose) => () => {
    setOpen(false);
    if (aggredToClose && returnProps.returnCb) returnProps.returnCb();
  };

  const renderButton = () => {
    return shouldWarn ? (
        <IconButton
          id={elementId}
          color="primary"
          aria-label="Return"
          size="large"
          onClick={handleClickOpen}
          sx={{ mx: 1, backgroundColor: "#f1f1f1" }}
        ><UndoIcon/></IconButton>
      ) :
      returnProps.returnCb ? (
        <IconButton
          id={elementId}
          color="primary"
          aria-label="Return"
          size="large"
          sx={{ mx: 1, backgroundColor: "#f1f1f1" }}
          onClick={() => returnProps.returnCb()}
        ><UndoIcon/></IconButton>
      ) : (
        <NextLink href={returnProps.uri} passHref>
          <IconButton
            id={elementId}
            color="primary"
            aria-label="Return"
            size="large"
            sx={{ mx: 1, backgroundColor: "#f1f1f1" }}
          ><UndoIcon/></IconButton>
        </NextLink>
      );
  };

  const renderDialog = () => {
    return (shouldWarn && <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">
        {returnProps.title}
      </DialogTitle>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          {returnProps.message}
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose(false)}>Disagree</Button>
        <NextLink href={returnProps.uri} passHref>
          <Button onClick={handleClose(true)} autoFocus>
              Agree
          </Button>
        </NextLink>
      </DialogActions>
    </Dialog>
    );
  };

  return (
    <div>
      {renderButton()}
      {renderDialog()}
    </div>
  );
}

export const SimpleReturnButton = ({ handleClose }) => {
  return (
    <IconButton
      color="primary"
      aria-label="Return"
      size="large"
      onClick={handleClose}
      sx={{ mx: 1, backgroundColor: "#f1f1f1" }}
    ><UndoIcon/></IconButton>
  );
};
