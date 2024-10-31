"use client";

import * as React from "react";

// MUI
import { Card, CardContent, Container, Grid, Typography } from "@mui/material";

// Components
import Breadcrumb from "@/app/ui/Breadcrumb";
import Layout from "@/app/ui/UnauthorizedLayout";

const breadcrumbList = [
  {
    label: "Home",
    url: "/",
  },
];

function UnAuthorized() {
  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <Card sx={{ width: "100%", marginBottom: 4 }}>
          <CardContent>
            <Grid container spacing={2}>
              <Grid item xs={12} lg={12} md={12} textAlign="center">
                <Typography>
                  Please contact an Administrator
                </Typography>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      </Container>
    </Layout>
  );
}

export default function UnAuthorizedComponent() {
  return (
    <React.Suspense fallback={null}>
      <UnAuthorized/>
    </React.Suspense>
  );
}
