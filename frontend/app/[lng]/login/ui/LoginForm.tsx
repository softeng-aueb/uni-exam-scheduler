"use client";

import { signIn } from "next-auth/react";
import * as yup from "yup";
import Image from "next/image";
import React, { useState } from "react";
import { useFormik } from "formik";

// MUI
import AccountCircle from "@mui/icons-material/AccountCircle";
import GoogleIcon from "@mui/icons-material/Google";
import IconButton from "@mui/material/IconButton";
import LockIcon from "@mui/icons-material/Lock";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { Box, Button, Card, CardContent, InputAdornment, TextField } from "@mui/material";

// Components
import logo from "../../../../public/images/logos/logo.svg";

const validationSchema = yup.object({
  email: yup.string().required("Email is required"),
  password: yup
    .string()
    .min(8, "Password should be of minimum 8 characters length")
    .required("Password is required"),
});

export default function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleMouseDownPassword = (event: React.MouseEvent) => {
    event.preventDefault();
  };

  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const result: any = await signIn("credentials", {
        email: values.email,
        password: values.password,
        callbackUrl: "/",
      });

      console.log(`onSubmit > result: ${JSON.stringify(result)}`);

      if (result === "CredentialsSignin") {
        setErrorMsg("Invalid credentials");
      } else {
        window.location.href = "/";
      }
    },
  });

  return (
    <Box sx={{ left: "50%", position: "absolute", top: "50%", transform: "translate(-50%, -50%)" }}>
      <Card sx={{ width: "400px" }}>
        <CardContent sx={{ display: "flex", flexDirection: "column", justifyContent: "center", }}>
          <Box sx={{ alignSelf: "center", marginBottom: "2rem" }}>
            <Image src={logo} width={256} height={128} alt="logo" unoptimized={true}/>
          </Box>
          <form>
            <Box sx={{ margin: "1rem 0" }}>
              <TextField
                InputProps={{
                  disableUnderline: true,
                  startAdornment: (
                    <InputAdornment position="start">
                      <AccountCircle/>
                    </InputAdornment>
                  ),
                }}
                id="email"
                placeholder="Email"
                fullWidth
                name="email"
                label="Email"
                value={formik.values.email}
                onChange={formik.handleChange}
                error={formik.touched.email && Boolean(formik.errors.email)}
                helperText={formik.touched.email && formik.errors.email}
              />
            </Box>
            <Box sx={{ margin: "1rem 0" }}>
              <TextField
                id="password"
                placeholder="Password"
                type={showPassword ? "text" : "password"}
                fullWidth
                name="password"
                label="Password"
                value={formik.values.password}
                onChange={formik.handleChange}
                error={formik.touched.password && Boolean(formik.errors.password)}
                helperText={formik.touched.password && formik.errors.password}
                InputProps={{
                  disableUnderline: true,
                  startAdornment: (
                    <InputAdornment position="start">
                      <LockIcon/>
                    </InputAdornment>
                  ),
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowPassword}
                        onMouseDown={handleMouseDownPassword}
                      >
                        {showPassword ? (
                          <VisibilityOff/>
                        ) : (
                          <Visibility/>
                        )}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
            </Box>
            <Box sx={{ margin: "1rem 0" }}>
              <Button
                id="loginButton"
                variant="contained"
                disabled={!formik.dirty}
                fullWidth={true}
                onClick={() => formik.handleSubmit()}
              >
                Login
              </Button>
            </Box>
            <Box sx={{ margin: "0" }}>
              <Button
                id="loginButton"
                variant="outlined"
                fullWidth={true}
                startIcon={<GoogleIcon/>}
                onClick={() => signIn("google", { callbackUrl: `${process.env.NEXTAUTH_URL}`, })}
              >
                Login with Google
              </Button>
            </Box>
            {errorMsg && <p style={{ color: "red" }}>{errorMsg}</p>}
          </form>
        </CardContent>
      </Card>
    </Box>
  );
}
