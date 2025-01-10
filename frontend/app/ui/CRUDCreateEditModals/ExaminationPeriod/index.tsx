import React, { useEffect, useState } from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

// Components
import CreateEditModalCommon from "../Common/ModalStyles";

import { onAction } from "../../Common/cruds";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";
import { createExaminationPeriod } from "@/app/lib/dbOperations";
import SelectComponent from "../../Form/SelectComponent";
import { DatePickerComponent } from "../../Form/DatePickerComponent";
import moment from "moment";

const PERIODS = [
  { label: "WINTER", value: "WINTER" },
  { label: "SPRING", value: "SPRING" },
  { label: "SEPTEMBER", value: "SEPTEMBER" },
]
export default function CreateEditModalExaminationPeriod(props: any) {
  const { open, handleClose, data, pageData, selectedYear, setData, lng } = props;

  const { t } = useTranslation(lng);

  const initialValues = {
    period: "",
    academicYear: "",
    startDate: ""
  };

  const validationSchema = yup.object({
    period: yup.string().trim().required("period is required"),
    startDate: yup.string().trim().required("start date is required"),
  });


  const spinnerArea = "create-examination-period";
  const headerTitle = "Create Examination Period";

  const handleContinue = async (values: any) => {
    const clonedVals = { ...values };
    const previousYear = pageData.find((d)=>d.name === selectedYear.label);
    clonedVals.academicYear = previousYear;
    const payload = {
      frontendFunc: setData,
      data,
    };

    try {
      const resp = await createExaminationPeriod(clonedVals);
      if (resp?.id) {
        clonedVals.id = resp?.id;
      }
      onAction(clonedVals, payload);
      handleClose();
    } catch (e: any) {
      console.log(e);
    }
  };

  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values: any) => await handleContinue(values)}
      >
        {({ values, handleSubmit, setFieldValue, errors, handleChange }) => (
          <Form>
            <Grid container spacing={2}>

              <Grid item xs={12}>
                <SelectComponent
                  menuItems={PERIODS}
                  value={values.period}
                  onSelect={handleChange("period")}
                  placeHolder="period"
                  hasError={!!errors.period}
                  errorMessage={errors.period as string}
                />

              </Grid>
             
              <Grid item xs={12}>
                <DatePickerComponent
                  value={moment(values.startDate).format("YYYY-MM-DD")}
                  label="Start Date"
                  onChange={(val: any) => {
                    const formattedDate = moment(val).format("YYYY-MM-DD");
                    setFieldValue("startDate", formattedDate);
                  }}
                  hasError={!!errors.startDate}
                  errorMessage={errors.startDate as string}
                />

              </Grid>
            </Grid>
            <Grid container spacing={2} marginTop={2}>
              <Grid item xs={12} sm={6}>
                <Button
                  type="submit"
                  disabled={false}
                  variant="contained"
                >
                  {t("examination.create_btn")}
                </Button>
              </Grid>
              <Grid item xs={12} sm={6}>
                <SpinnerWithPromise area={spinnerArea}/>
              </Grid>
            </Grid>
          </Form>
        )}
      </Formik>
    </CreateEditModalCommon>
  );
}
