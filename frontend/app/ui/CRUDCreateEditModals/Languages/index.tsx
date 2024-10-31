import * as React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";
import { UserActionButton } from "../../Buttons/modalButtons";

// Services
import { createLanguage, updateLanguage } from "@/app/lib/dbOperations/collections/Languages";
import { handleContinue } from "../../Common/cruds";
import CheckboxComponent from "../../Form/CheckBoxComponent";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalLanguages(props: any) {
  const { open, handleClose, language, data, setData, lng } = props;
  const { t } = useTranslation(lng);

  const initialValues = {
    active: language?.active || false,
    status: language?.status || "INACTIVE",
    order: language?.order || "",
    /* */
    name: language?.name || "",
    nativeName: language?.nativeName || "",
    code: language?.code || "",
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("name name is required"),
    nativeName: yup.string().trim().required("nativeName name is required"),
    code: yup.string().required("code is required"),
    active: yup.boolean(),
  });

  const spinnerArea = language?._id ? "update-language" : "create-language";
  const headerTitle = language?._id ? "Update Language" : "Create Language";

  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values) => {
          const payload = {
            frontendFunc: setData,
            apiFuncCreate: createLanguage,
            apiFuncUpdate: updateLanguage,
            data,
            handleClose,
          };
          await handleContinue(values, payload, language._id);
        }
        }
      >
        {({ values, handleSubmit, errors, setFieldValue, handleChange, dirty }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("languages.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("languages.field.nativeName")}
                    value={values.nativeName}
                    handleTextChange={handleChange("nativeName")}
                    hasError={!!errors.nativeName}
                    errorMessage={errors.nativeName as string}
                    id="nativeName"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("languages.field.code")}
                    value={values.code}
                    handleTextChange={handleChange("code")}
                    hasError={!!errors.code}
                    errorMessage={errors.code as string}
                    id="code"
                  />
                </Grid>
                <Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_language"
                  />
                </Grid>
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <UserActionButton
                    label={language?._id ? t("languages.update_btn") : t("languages.create_btn")}
                    type="submit"
                    handleClick={handleSubmit}
                    disabled={false}
                  />
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
