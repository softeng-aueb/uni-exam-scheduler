import React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";

// Components
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { TextInput } from "../../Form/InputComponent";
import { UserActionButton } from "../../Buttons/modalButtons";
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import { createRole, updateRole } from "@/app/lib/dbOperations/collections/UserRoles";

import SelectComponent from "../../Form/SelectComponent";
import { handleContinue } from "../../Common/cruds";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalUserRoles(props: any) {
  const { open, handleClose, role, setData, data, lng } = props;
  const { t } = useTranslation(lng);

  const validationSchema = yup.object({
    name: yup.string().trim().required("name is required"),
    permissions: yup.array(),
  });

  const initialValues = {
    active: role?.active || false,
    status: role?.status || "INACTIVE",
    order: role?.order || "",
    /* */
    name: role?.name || "",
    permissions: role?.permissions || [],
  };

  const spinnerArea = role._id ? "update-role" : "create-role";
  const headerTitle = role._id ? "Update Role" : "Create Role";

  const permissionsList = [
    { label: "*", value: "*" },
    { label: "/admin/*", value: "/admin/*" },
    { label: "/path1/*", value: "/path1/*" },
    { label: "/path2/*", value: "/path2/*" },
    { label: "/path3/*", value: "/path3/*" },
  ];

  const handlePermissionsChange = (e: any, setFieldValue: any) => {
    const val = e.target.value;
    setFieldValue("permissions", Array.isArray(val) ? val.sort() : val);
  };

  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values) => {
          const payload = {
            frontendFunc: setData,
            apiFuncCreate: createRole,
            apiFuncUpdate: updateRole,
            data,
            handleClose,
          };
          await handleContinue(values, payload, role._id);
        }}
      >
        {({ values, handleChange, handleSubmit, errors, touched, setFieldValue }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("roles.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <SelectComponent
                    menuItems={permissionsList}
                    value={values.permissions}
                    onSelect={(e: any) => handlePermissionsChange(e, setFieldValue)}
                    hasError={!!errors.permissions}
                    errorMessage={errors.permissions as string}
                    placeHolder={"Select Permissions"}
                    id="permissions"
                    multiple
                  />
                </Grid>

              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <UserActionButton
                    label={role?._id ? t("roles.update_btn") : t("roles.create_btn")}
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
