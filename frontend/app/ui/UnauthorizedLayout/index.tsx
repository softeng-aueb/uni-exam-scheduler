"use client";

import React, { useState } from "react";

// MUI
import { createTheme, ThemeProvider } from "@mui/material/styles";

let theme = createTheme();

type Props = {
  children: React.ReactNode;
  session?: any;
};

export default function Layout({ children }: Props) {
  const [loading, setLoading] = useState(false);

  if (loading) return null; // this will prevent screen flickering

  return (
    <ThemeProvider theme={theme}>
      {children}
    </ThemeProvider>
  );
}
