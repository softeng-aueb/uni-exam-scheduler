import React, { useState } from "react";
import { FormControl, FormHelperText, IconButton, Input, InputAdornment, InputLabel } from "@mui/material";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { Add } from "@mui/icons-material";

interface Props {
  placeHolder: string;
  value: string | number | null;
  hasError?: boolean;
  errorMessage?: string;
  handleTextChange: Function;
  handleSubmit?: Function;
  disabled?: boolean;
  required?: boolean;
  type?: string;
  inputStyles?: any;
  ref?: any;
  id?: string;
  multiline?: boolean
  rows?: number
  hasStartAdornment?: boolean
  inputProps?: unknown
  hasCommaSeperatedEmails?: boolean;
}

const TextInput = (props: Props) => {
  const {
    placeHolder,
    value,
    hasError,
    errorMessage,
    handleTextChange,
    handleSubmit = () => {
    },
    disabled = false,
    required = false,
    type,
    inputStyles,
    ref,
    id,
    multiline = false,
    rows = 1,
    hasStartAdornment = false,
    inputProps,
    hasCommaSeperatedEmails,
  } = props;
  const formattedValue = "textField-outline";
  return (
    <FormControl
      sx={{ m: 1 }}
      fullWidth
      variant="standard"
      style={{
        margin: "0px",
      }}
    >
      {placeHolder && <InputLabel htmlFor={formattedValue} required={required} error={hasError}>
        {placeHolder}
      </InputLabel>}
      <Input
        id={id || formattedValue}
        type={type === "number" ? "number" : "text"}
        value={value}
        error={hasError}
        disabled={disabled}
        // label={placeHolder}
        required={required}
        onChange={(e) => handleTextChange(hasCommaSeperatedEmails ? e.target.value.replaceAll(" ", "").split(",") : e.target.value)}
        onKeyDown={(e) => {
          if (e.key === "Enter") handleSubmit();
        }}
        aria-describedby={"component-error"}
        style={inputStyles}

        // inputProps={inputStyles}
        inputProps={inputProps}
        ref={ref ? ref : null}
        multiline={multiline ? multiline : false}
        rows={rows}
        startAdornment={
          hasStartAdornment &&
          <InputAdornment position="start">
            <Add/>
          </InputAdornment>
        }

      />
      <FormHelperText
        style={{
          display: hasError ? "block" : "none",
          color: "red",
          fontSize: 10,
        }}
        id={"component-error"}
      >
        {errorMessage}
      </FormHelperText>
    </FormControl>
  );
};

const PasswordInput = (props: Props) => {
  const {
    placeHolder,
    value,
    hasError,
    errorMessage,
    handleTextChange,
    handleSubmit = () => {
    },
  } = props;
  const [showPassword, setShowPassword] = useState(false);

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <FormControl
      sx={{ m: 1 }}
      fullWidth
      variant="standard"
      style={{ margin: "0px" }}
    >
      <InputLabel
        htmlFor="outlined-adornment-password"
        required
        error={hasError}
      >
        {placeHolder}
      </InputLabel>
      <Input
        id="outlined-adornment-password"
        type={showPassword ? "text" : "password"}
        value={value}
        error={hasError}
        // label={placeHolder}
        onChange={(e) => handleTextChange(e.target.value)}
        onKeyUp={(e) => {
          if (e.key === "Enter") handleSubmit();
        }}
        endAdornment={
          <InputAdornment position="end">
            <IconButton
              aria-label="toggle password visibility"
              onClick={handleClickShowPassword}
              edge="end"
            >
              {showPassword ? <VisibilityOff/> : <Visibility/>}
            </IconButton>
          </InputAdornment>
        }
      />
      <FormHelperText
        style={{
          display: hasError ? "block" : "none",
          color: "red",
          fontSize: 10,
        }}
        id={"component-error-password"}
      >
        {errorMessage}
      </FormHelperText>
    </FormControl>
  );
};

export { TextInput, PasswordInput };
