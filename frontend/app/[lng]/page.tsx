"use client";

import React, { useEffect } from "react";
import { useRouter } from "next/navigation";
import { SessionProvider, useSession } from "next-auth/react";

import CalendarEventsComponent from "@/app/[lng]/calendar-events/ui/CalendarEventsComponent";
import Layout from "@/app/ui/layout";

export default function Page() {
  const { data: session, status }: any = useSession();

  const router: any = useRouter();

  useEffect(() => {
    console.log(`${new Date().toISOString()} > page.tsx > useEffect > status: ${JSON.stringify(status)}`);

    if (status === "unauthenticated") {
      router.push("/login");
    }
  }, [session]);

  return (<SessionProvider><CalendarEventsComponent/></SessionProvider>);
}
