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
import { createDepartment, updateDepartment } from "@/app/lib/dbOperations/collections/Departments";
import { handleContinue } from "../../Common/cruds";
import CheckboxComponent from "../../Form/CheckBoxComponent";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalDepartments(props: any) {
  const { open, handleClose, department, data, setData, lng } = props;
  const { t } = useTranslation(lng);

  const initialValues = {
    active: department?.active || false,
    status: department?.status || "INACTIVE",
    order: department?.order || "",
    /* */
    name: department?.name || "",
    nativeName: department?.nativeName || "",
    code: department?.code || "",
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("name name is required"),
    nativeName: yup.string().trim().required("nativeName name is required"),
    code: yup.string().required("code is required"),
    active: yup.boolean(),
  });

  const spinnerArea = department?._id ? "update-department" : "create-department";
  const headerTitle = department?._id ? "Update Department" : "Create Department";

  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values) => {
          const payload = {
            frontendFunc: setData,
            apiFuncCreate: createDepartment,
            apiFuncUpdate: updateDepartment,
            data,
            handleClose,
          };
          await handleContinue(values, payload, department._id);
        }
        }
      >
        {({ values, handleSubmit, errors, setFieldValue, handleChange, dirty }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("departments.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("departments.field.nativeName")}
                    value={values.nativeName}
                    handleTextChange={handleChange("nativeName")}
                    hasError={!!errors.nativeName}
                    errorMessage={errors.nativeName as string}
                    id="nativeName"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("departments.field.code")}
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
                    id="active_department"
                  />
                </Grid>
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <UserActionButton
                    label={department?._id ? t("departments.update_btn") : t("departments.create_btn")}
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
