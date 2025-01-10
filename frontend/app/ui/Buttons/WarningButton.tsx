import React from "react";

// MUI
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Tooltip from "@mui/material/Tooltip";

// Component
import FabButton from "./FabButton";

// Interface
import { IWarningDialogProps } from "../../lib/dbOperations/schemas/ComponentsInterfaces";

export default function AlertDialog(props: IWarningDialogProps) {
  const {
    type,
    title,
    message,
    tooltip,
    agreeText = "Agree",
    disagreeText = " Disagree",
    disabled = false,
    // children,
    label,
    onClickAgree,
    onClickDisagree,
  } = props;
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (agreed: boolean) => async () => {
    if (agreed && onClickAgree !== undefined) await onClickAgree();
    if (!agreed && onClickDisagree !== undefined) await onClickDisagree();
    setOpen(false);
  };

  return (
    <div>
      <Tooltip title={tooltip}>
        <FabButton
          label="remove-schedule"
          onClick={handleClickOpen}
          disabled={disabled}
        >
          {label}
        </FabButton>
      </Tooltip>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {title}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {message}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose(false)}>{disagreeText}</Button>
          <Button onClick={handleClose(true)} autoFocus>
            {agreeText}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
