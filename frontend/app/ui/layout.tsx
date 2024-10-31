"use client";

import React, { useEffect, useState } from "react";
import { useSession } from "next-auth/react";
import { Lato } from "next/font/google";

// MUI
import { createTheme, Theme, ThemeProvider } from "@mui/material/styles";

import BasicAppBar from "../ui/BasicAppBar"

const lato = Lato({ preload: false, subsets: ["latin"], weight: ["100", "300", "400", "700", "900"] });

let theme: Theme = createTheme({
  typography: {
    fontFamily: [lato.style.fontFamily, "sans-serif"].join(","),
  },
  components: {
    MuiCard: {
      styleOverrides: {
        root: {
          fontFamily: [lato.style.fontFamily, "sans-serif"].join(","),
        },
      }
    },
    MuiModal: {
      styleOverrides: {
        root: {
          fontFamily: [lato.style.fontFamily, "sans-serif"].join(","),
        },
      }
    },
  },
});

type Props = {
  children: React.ReactNode;
  session?: any;
};

export default function Layout({ children }: Props) {
  const [loading, setLoading] = useState(true);

  const { data: session }: any = useSession();

  useEffect(() => {
    if (!session) {
      // router.push("/login");
    } else {
      setLoading(false);
    }
  }, [session]);

  if (loading) return null; // this will prevent screen flickering

  return (
    <ThemeProvider theme={theme}>
      <BasicAppBar/>
      {children}
    </ThemeProvider>
  );
}
