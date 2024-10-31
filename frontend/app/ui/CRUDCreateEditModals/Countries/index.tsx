import * as React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import { createCountry, updateCountry } from "@/app/lib/dbOperations/collections/Countries";
import { onAction } from "../../Common/cruds";
import CheckboxComponent from "../../Form/CheckBoxComponent";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalCountries(props: any) {
  const { open, handleClose, country, data, setData, lng } = props;
  const { t } = useTranslation(lng);

  const initialValues = {
    active: country?.active || false,
    status: country?.status || "INACTIVE",
    order: country?.order || "",
    /* */
    name: country?.name || "",
    iso2: country?.iso2 || "",
    iso3: country?.iso3 || "",
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("name name is required"),
    iso2: yup.string().trim().required("iso2 name is required"),
    iso3: yup.string().trim().required("iso3 is required"),
    order: yup.string().trim().required("order is required"),
    active: yup.boolean(),
  });

  const spinnerArea = country?._id ? "update-country" : "create-country";
  const headerTitle = country?._id ? "Update Country" : "Create Country";

  const handleContinue = async (values: any) => {
    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const { data: resp }: any = country?._id ? await updateCountry(country._id, values) : await createCountry(values);
      if (resp?.insertedId) {
        values._id = resp?.insertedId;
      } else {
        values._id = country._id;
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
        {({ values, handleSubmit, errors, setFieldValue, handleChange, dirty }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("countries.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("countries.field.iso2")}
                    value={values.iso2}
                    handleTextChange={handleChange("iso2")}
                    hasError={!!errors.iso2}
                    errorMessage={errors.iso2 as string}
                    id="iso2"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("countries.field.iso3")}
                    value={values.iso3}
                    handleTextChange={handleChange("iso3")}
                    hasError={!!errors.iso3}
                    errorMessage={errors.iso3 as string}
                    id="iso3"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("countries.field.order")}
                    value={values.order}
                    handleTextChange={handleChange("order")}
                    hasError={!!errors.order}
                    errorMessage={errors.order as string}
                    id="order"
                  />
                </Grid>
                <Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_country"
                  />
                </Grid>
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                <Button
                  type="submit"
                  onClick={()=> handleSubmit()}
                  disabled={false} 
                  variant="contained"   
                >
                  {country?._id ? t("countries.update_btn") : t("countries.create_btn")}
                </Button>
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <SpinnerWithPromise area={spinnerArea}/>
                </Grid>
              </Grid>
            </Form>
          );
        }}
      </Formik>
    </CreateEditModalCommon>

  );
}
