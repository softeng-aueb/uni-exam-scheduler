"use client";

import React, { useCallback, useEffect, useState } from "react";

// MUI
import { Card, CardContent, Container, Grid } from "@mui/material";

// FullCalendar
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";

// Components
import Layout from "@/app/ui/layout";
import Spinner from "@/app/ui/Spinner";
import ViewModalCalendarEvents from "../../../ui/CRUDViewModals/CalendarEvents";
import { fetchCalendarEvents } from "@/app/lib/dbOperations/collections/CalendarEvents";

export default function CalendarEventsComponent({ lng }: any) {
  const [defaultView, setDefaultView] = useState("dayGridMonth");
  const [eventData, setEventData] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [viewModalData, setViewModalData] = useState("");
  const [viewModalTitle, setViewModalTitle] = useState("");

  const handleCloseModal = () => setModalOpen(false);

  useEffect(() => {
    async function init() {
      setLoading(true);
      const { data: schedules } = await fetchCalendarEvents();
      const formattedData = formatCalendarData(schedules);
      setEventData(formattedData);
      setLoading(false);
    }

    init();
  }, []);

  const handleEventClick = (clickInfo: any) => {
    const title = clickInfo.event.title;
    const data = clickInfo.event.extendedProps.obj;

    setViewModalTitle(title);
    setViewModalData(data);
    setModalOpen(true);
  };

  const updateData = useCallback((values: any) => {
    setEventData(eventData.map((d: any) => d.obj.id === values.id ? {
      ...d,
      title: values.title,
      [d.obj.supervisions]: values.supervisions,
    } : d));
  }, [eventData]);

  const formatCalendarData = (data: any) => {
    const formattedData: any = [];
    data?.forEach((schedule: any) => {
      const title = `${schedule?.course?.title} [${schedule?.course?.courseCode}]`;
      const start = new Date(`${schedule.date} ${schedule.startTime}`);
      const end = new Date(`${schedule.date} ${schedule.endTime}`);
      const color = "#aee8a3";
      const textColor = "#3da729";

      formattedData.push({ title, start, end, color, textColor, obj: schedule });
    });
    return formattedData;
  };

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Card sx={{ width: "100%", marginBottom: 4 }}>
          <CardContent>
            <Grid container spacing={2}>
              <Grid item lg={12}>
                {loading && <Spinner/>}
                {eventData?.length > 0 && <FullCalendar
                  headerToolbar={{
                    left: "prev,today,next",
                    center: "title",
                    right: "timeGridDay,timeGridWeek,dayGridMonth",
                  }}
                  expandRows={true}
                  plugins={[dayGridPlugin, timeGridPlugin]}
                  initialView={defaultView}
                  allDaySlot={false}
                  contentHeight="auto"
                  firstDay={1}
                  events={eventData}
                  eventClick={handleEventClick}
                  eventTimeFormat={{
                    hour: "2-digit",
                    minute: "2-digit",
                    hour12: false,
                  }}
                />}
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      </Container>
      <ViewModalCalendarEvents
        open={modalOpen}
        handleClose={handleCloseModal}
        title={viewModalTitle}
        data={viewModalData}
        updateData={updateData}
      />
    </Layout>
  );
}
