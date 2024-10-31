import React, { useEffect, useState } from "react";
import { Alert, Snackbar } from "@mui/material";

export interface SnackBarParamType {
  message: string,
  severity: "success" | "error" | "warning" | "info"
}

export interface PayloadType extends SnackBarParamType {
  autoHideDuration?: number,
}

export default function SnackBar({ payload }: { payload: PayloadType }) {
  const [open, setOpen] = useState(false);
  const [duration, setDuration] = useState(5000);
  const [snackBarProps, setSnackBarProps] = useState<SnackBarParamType>({ message: "", severity: "error" });

  useEffect(() => {
    const { message, severity, autoHideDuration } = payload;
    if (message === "") return;
    if (autoHideDuration) setDuration(autoHideDuration);
    setSnackBarProps({ message, severity });
    setOpen(true);
  }, [payload]);

  const handleCloseSnackBar = (event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === "clickaway") {
      return;
    }
    setDuration(5000);
    setSnackBarProps({ message: "", severity: "error" });
    setOpen(false);
  };

  return (
    <Snackbar open={open} autoHideDuration={duration} onClose={handleCloseSnackBar}>
      <Alert
        onClose={handleCloseSnackBar}
        severity={snackBarProps.severity}
        sx={{ width: "100%" }}
      >
        {snackBarProps.message}
      </Alert>
    </Snackbar>
  );
};
