"use client";

import React from "react";
import { I18nextProvider } from "react-i18next";

// MUI
import { Box, Grid, Typography } from "@mui/material";

// Utils
import { useTranslation } from "@/app/i18n/client";

export default function NotFoundComponent({ lng }: any) {
  const { t, i18n } = useTranslation(lng);

  return (
    <I18nextProvider i18n={i18n}>
      <main>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Box>
              <Typography>
                404
              </Typography>
              <Typography>
                {t("page_not_found.NOT_FOUND")}
              </Typography>
            </Box>
          </Grid>
        </Grid>
      </main>
    </I18nextProvider>
  );
}
