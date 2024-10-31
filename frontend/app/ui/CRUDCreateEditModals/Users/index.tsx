import React, { useState } from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import InputAdornment from "@mui/material/InputAdornment";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import EmailIcon from "@mui/icons-material/Email";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import Visibility from "@mui/icons-material/Visibility";

// Components
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { UserActionButton } from "../../Buttons/modalButtons";
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import CheckboxComponent from "../../Form/CheckBoxComponent";
import SelectComponent from "@/app/ui/Form/SelectComponent";
import { createUser, editUser } from "@/app/lib/dbOperations";
import { handleContinue } from "../../Common/cruds";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalUsers(props: any) {
  const { open, handleClose, user, setData, data, roles, lng } = props;
  const { t } = useTranslation(lng);

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const validationSchema = yup.object({
    username: yup.string().required("Username is required"),
    email: yup.string().required("Email is required"),
    is_admin: yup.boolean(),
    roles: yup.array().required("UserRoles is required"),
    password: user._id ?
      yup.string().notRequired().min(8, "Password should be of minimum 8 characters length") :
      yup.string().min(8, "Password should be of minimum 8 characters length").required("Password is required"),
    confirm_password: yup.string().when("password", {
      is: (val: any) => (val && val.length > 0),
      then: () => yup.string().required("Confirm Password is required").oneOf([yup.ref("password")], "Both password need to be the same"),
    }),
  });

  const initialValues = {
    active: user?.active || false,
    status: user?.status || "INACTIVE",
    order: user?.order || "",
    /* */
    username: user?.username || "",
    email: user?.email || "",
    is_admin: user?.is_admin || "",
    roles: user?.roles || [],
    password: "",
    confirm_password: "",
  };

  const spinnerArea = user._id ? "update-user" : "create-user";
  const headerTitle = user._id ? "Update User" : "Create User";

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleClickShowConfirmPassword = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  const handleRoleChange = (e: any, setFieldValue: any) => {
    const val = e.target.value;
    setFieldValue("roles", Array.isArray(val) ? val.sort() : val);
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
            apiFuncCreate: createUser,
            apiFuncUpdate: editUser,
            data,
            handleClose,
          };
          await handleContinue(values, payload, user._id);
        }}
      >
        {({ values, handleChange, handleSubmit, errors, touched, setFieldValue }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12} md={6}>
                  <TextField
                    id="username"
                    variant="standard"
                    placeholder="Username"
                    fullWidth
                    name="username"
                    label="Username"
                    value={values.username}
                    onChange={handleChange}
                    error={touched.username && Boolean(errors.username)}
                    helperText={touched.username && errors.username as string}
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton>
                            <AccountCircleIcon/>
                          </IconButton>
                        </InputAdornment>
                      ),
                    }}
                  />
                </Grid>
                <Grid item xs={12} md={6}>
                  <TextField
                    id="email"
                    variant="standard"
                    placeholder="Email Address"
                    fullWidth
                    name="email"
                    label="Email Address"
                    value={values.email}
                    onChange={handleChange}
                    error={touched.email && Boolean(errors.email)}
                    helperText={touched.email && errors.email as string}
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton>
                            <EmailIcon/>
                          </IconButton>
                        </InputAdornment>
                      ),
                    }}
                  />
                </Grid>
                <Grid item xs={12} md={6}>
                  <TextField
                    id="password"
                    variant="standard"
                    placeholder="Password"
                    fullWidth
                    autoComplete="off"
                    type={showPassword ? "text" : "password"}
                    name="password"
                    label="Password"
                    value={values.password}
                    onChange={handleChange}
                    error={touched.password && Boolean(errors.password)}
                    helperText={touched.password && errors.password}
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle password visibility"
                            onClick={handleClickShowPassword}
                          >
                            {showPassword ? <VisibilityOff/> : <Visibility/>}
                          </IconButton>
                        </InputAdornment>
                      ),
                    }}
                  />
                </Grid>
                <Grid item xs={12} md={6}>
                  <TextField
                    id="confirm_password"
                    variant="standard"
                    placeholder="Confirm Password"
                    fullWidth
                    type={showConfirmPassword ? "text" : "password"}
                    name="confirm_password"
                    label="Confirm Password"
                    value={values.confirm_password}
                    onChange={handleChange}
                    error={
                      touched.confirm_password && Boolean(errors.confirm_password)
                    }
                    helperText={
                      touched.confirm_password && errors.confirm_password
                    }
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="confirm password visibility"
                            onClick={handleClickShowConfirmPassword}
                          >
                            {showConfirmPassword ? (
                              <VisibilityOff/>
                            ) : (
                              <Visibility/>
                            )}
                          </IconButton>
                        </InputAdornment>
                      ),
                    }}
                  />
                </Grid>

                <Grid item xs={12} md={6}>
                  <SelectComponent
                    menuItems={roles?.map((role: any) => {
                      return {
                        label: role.name,
                        value: role.name,
                      };
                    })}
                    value={values.roles}
                    onSelect={(e: any) => handleRoleChange(e, setFieldValue)}
                    hasError={!!errors.roles}
                    errorMessage={errors.roles as string}
                    placeHolder={"Select Role(s)"}
                    id="roles"
                    multiple
                  />
                </Grid>

                <Grid item xs={12} md={6}>
                  <CheckboxComponent
                    checked={values.is_admin}
                    handleChange={(e: any) => setFieldValue("is_admin", e.target.checked)}
                    label="Administrator"
                    id="is_admin"
                  />
                </Grid>
                <Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_listing"
                  />
                </Grid>
              </Grid>

              {/* TODO change styling */}
              <br/>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <UserActionButton
                    label={user?._id ? t("users.update_btn") : t("users.create_btn")}
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
