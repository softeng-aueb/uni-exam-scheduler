"use client";

import React from "react";
import { LicenseInfo } from "@mui/x-license-pro";

import { SessionProvider } from "next-auth/react";

LicenseInfo.setLicenseKey(
  // "603c58eecffbdca9834c1c1ed0bfe0edTz00Mzk4MCxFPTE2ODQ0ODY3MDYzOTcsUz1wcm8sTE09c3Vic2NyaXB0aW9uLEtWPTI=",
  // "db74bae1219e78469ee2963bcb8dcf4bTz02NzAwMixFPTE3MTYzODIzOTA2OTMsUz1wcm8sTE09c3Vic2NyaXB0aW9uLEtWPTI=",
  "f878edf5a8851ee6c57afbb33cc179a4Tz04OTM2NSxFPTE3NDYwMTUxOTIwMDAsUz1wcm8sTE09c3Vic2NyaXB0aW9uLEtWPTI=",
);

export default function AppProvider({ children }: { children: any }) {
  return (
    <SessionProvider basePath={process.env.BASE_PATH || ""}>
      {children}
    </SessionProvider>
  );
}
