import * as React from "react";
import { Metadata } from "next";

import AdminComponent from "./AdminComponent";

export const metadata: Metadata = {
  title: "Configuration",
  description: "Configuration",
};

export default function ConfigurationPage() {
  return (
    <AdminComponent/>
  );
}
