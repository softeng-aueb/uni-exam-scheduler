import React from "react";
import { Metadata } from "next";

import CalendarEventsComponent from "./ui/CalendarEventsComponent";

export const metadata: Metadata = {
  title: "Calendar Events",
  description: "Calendar Events",
};

export default function CalendarEventsPage({ params: { lng } }: any) {
  return <CalendarEventsComponent lng={lng}/>;
}
