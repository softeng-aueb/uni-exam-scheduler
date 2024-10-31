import React, { ReactNode } from "react";
import Fab from "@mui/material/Fab";

export type FabButtonTypes = {
  expanded?: boolean;
  label: string;
  href?: string;
  onClick?: any;
  children: ReactNode;
  inputStyles?: any;
  color?: "inherit" | "default" | "primary" | "secondary";
  disabled?: boolean;
};

export default function FabButton({
  expanded,
  label,
  onClick,
  href,
  children,
  inputStyles,
  color,
  disabled,
}: FabButtonTypes) {
  return (
    <Fab
      aria-controls="basic-menu"
      aria-expanded={expanded ? "true" : undefined}
      aria-haspopup="true"
      aria-label={label}
      onClick={onClick}
      size="small"
      href={href}
      sx={inputStyles}
      color={color || "primary"}
      disabled={disabled}
    >
      {children}
    </Fab>
  );
}
