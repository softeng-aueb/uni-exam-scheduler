"use client";

import React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";

// Components
import CreateEditModalCommon from "../../CRUDCreateEditModals/Common/ModalStyles";
import DataView from "../../DataView";
import GroupingBox from "../../GroupingBox";
import SelectComponent from "@/app/ui/Form/SelectComponent";
import { UserActionButton } from "@/app/ui/Buttons/modalButtons";

// Services
import { updateCalendarEvent } from "@/app/lib/dbOperations/collections/CalendarEvents";

export default function ViewModalCalendarEvents(props: any) {
  const { open, handleClose, title, data, updateData } = props;

  const spinnerArea = "update-billing";

  const initialValues = {
    status: data?.status || "",
  };

  const validationSchema = yup.object({});
  const onContinue = async (values: any) => {
    try {
      await updateCalendarEvent(data._id, values);
      updateData({ id: data._id, status: values.status });
      handleClose();
    } catch (e: any) {
      console.log(e);
    }
  };

  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={title}>
      <pre>{JSON.stringify(data, null, 2)}</pre>
      <DataView
        compact={true}
        data={[/*{
            key: "ALL",
            val: JSON.stringify(data),
          }, {
          key: "Job ID",
          val: data?.course?.courseCode,
        }, {
          key: "Status",
          val: data?.data?.course?.title,
        }*/]}
      />

      <br/>

      {["CANCELED", "PAUSED", "RUNNING", "SCHEDULED"].includes(data?.status) && <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values: any) => onContinue(values)}
      >
        {({ values, handleSubmit, errors, setFieldValue, handleChange, dirty }) => {
          return (<Form>
            <GroupingBox marginBottom={0}>
              <Grid container spacing={2}>
                <Grid item xs>
                  <SelectComponent
                    menuItems={[
                      { label: "CANCELED", value: "CANCELED" },
                      { label: "PAUSED", value: "PAUSED" },
                      { label: "RUNNING", value: "RUNNING" },
                      { label: "SCHEDULED", value: "SCHEDULED" },
                    ]}
                    value={values.status}
                    onSelect={(e: any) => setFieldValue("status", e.target.value)}
                    hasError={!!errors.status}
                    errorMessage={errors.status as string}
                    placeHolder={"Job Status"}
                    id="status"
                  />
                </Grid>
                <Grid item sx={{ width: "220px" }}>
                  <UserActionButton
                    label={"Update Job Status"}
                    type="submit"
                    handleClick={handleSubmit}
                    disabled={false}
                  />
                </Grid>
                {/* <Grid item xs={12} sm={6} md={6} lg={6}>
                  <SpinnerWithPromise area={spinnerArea}/>
                </Grid>*/}
              </Grid>
            </GroupingBox>
          </Form>);
        }}
      </Formik>}
    </CreateEditModalCommon>
  );
}
