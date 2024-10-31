import * as React from "react";

// MUI
import { Box, Breadcrumbs, Link, Typography } from "@mui/material";

export default function Breadcrumb({ links }: any/* : { links: [BreadcrumbLinkElement] }*/) {
  const renderedLists = links.map(({ label, url }: any, i: number, arr: any) => {
    if (i === arr.length) {
      return <Typography key={i + label} color="text.primary">{label}</Typography>;
    }
    return (
      <Link key={i + label} underline="hover" color="inherit" href={url}>{label}</Link>
    );
  });

  return (
    <Box role="presentation" maxWidth="lg" sx={{ marginBottom: 4 }}>
      <Breadcrumbs maxItems={8} separator="›" aria-label="breadcrumb">
        {renderedLists}
      </Breadcrumbs>
    </Box>
  );
}
