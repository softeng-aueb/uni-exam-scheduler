import React, { useEffect, useState } from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";

import { onAction } from "../../Common/cruds";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";
import { createAcademicYear } from "@/app/lib/dbOperations";
import SelectComponent from "../../Form/SelectComponent";

export default function CreateEditModalAcademicYear(props: any) {
  const { open, handleClose, data, setData, lng } = props;

  const [yearForSelect, setYearForSelect] = useState([]);
  const { t } = useTranslation(lng);

  useEffect(() => {
    const formattedYears = data?.map((year) => ({ label: year.name, value: year.name }))
      .sort((a, b) => b.value.localeCompare(a.value));

    setYearForSelect(formattedYears);
  }, [data]);

  const initialValues = {
    name: "",
    previousYear: yearForSelect.length ? yearForSelect[0].value : ""
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("Name is required"),
    previousYear: yup
      .string()
      .required("Previous year is required")
      .test("valid-year", "Selected wrong previous year", (value) => {
        return yearForSelect.length && value === yearForSelect[0].value;
      })
  });


  const spinnerArea = "create-academic-year";
  const headerTitle = "Create Academic Year";

  const handleContinue = async (values: any) => {
    const clonedVals = { ...values }
    const previousYear = data.find((d) => d.name === values.previousYear);
    clonedVals.previousYear = previousYear;
    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const resp = await createAcademicYear(clonedVals);
      if (resp?.id) {
        values.id = resp?.id;
      }
      onAction(values, payload);
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
        {({ values, handleSubmit, errors, handleChange }) => (
          <Form>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextInput
                  placeHolder={t("academic.field.name")}
                  value={values.name}
                  handleTextChange={handleChange("name")}
                  hasError={!!errors.name}
                  errorMessage={errors.name as string}
                  id="name"
                />
              </Grid>
              <Grid item xs={12}>
                <SelectComponent
                  menuItems={yearForSelect}
                  value={values.previousYear}
                  onSelect={handleChange("previousYear")}
                  placeHolder="previousYear"
                />
                {errors.previousYear && <p style={{ color: "red" }}>{errors.previousYear as string}</p>}
              </Grid>
            </Grid>
            <Grid container spacing={2} marginTop={2}>
              <Grid item xs={12} sm={6}>
                <Button
                  type="submit"
                  onClick={() => handleSubmit()}
                  disabled={false}
                  variant="contained"
                >
                  {t("academic.create_btn")}
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
