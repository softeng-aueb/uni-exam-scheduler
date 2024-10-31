import React, { useEffect, useState } from "react";

import TextField from "@mui/material/TextField";
import { AdapterMoment } from "@mui/x-date-pickers/AdapterMoment";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";

interface DatePickerProps {
  label: String;
  value: any; // Date
  onChange: any;
  minDate?: Date;
  disabled?: boolean;
  hasError?: boolean;
  errorMessage?: string;
}

export const DatePickerComponent = (props: DatePickerProps) => {
  const { label, value, onChange, minDate, disabled, errorMessage } = props;
  const [hasError, setHasError] = useState(false);

  return (
    <LocalizationProvider dateAdapter={AdapterMoment}>
      <DatePicker
        minDate={minDate}
        label={label}
        value={value}
        onChange={onChange}
        disabled={disabled}
        renderInput={(params) => (
          <TextField
            variant="standard"
            fullWidth
            {...params}
            disabled={disabled}
            helperText={
              hasError ? <p style={{ color: "red" }}>{errorMessage ? errorMessage : "Invalid Date"}</p> : ""
            }
          />
        )}
        inputFormat="DD/MM/YYYY"
        onError={(reason) => {
          console.log("DatePicker onError reason:", reason);
          setHasError(!!reason);
        }}
      />
    </LocalizationProvider>
  );
};

type DateTimePickerProps = {
  label: string;
  value: any;
  onChange: any;
  disabled?: boolean;
  minDate?: Date;
  maxDate?: Date;
  hasError?: boolean;
  errorMessage?: string;
};

export const DateTimePickerComponent = (props: DateTimePickerProps) => {
  const { label, value, onChange, disabled, minDate, maxDate, hasError: error, errorMessage } = props;
  const [hasError, setHasError] = useState(error || false);

  const minDateTime = minDate !== undefined ? { minDateTime: minDate } : {};

  useEffect(() => {
  }, [error]);

  return (
    <LocalizationProvider dateAdapter={AdapterMoment}>
      <DateTimePicker
        maxDate={maxDate}
        label={label}
        value={value}
        onChange={onChange}
        disabled={disabled}
        {...minDateTime}
        renderInput={(params) => (
          <TextField
            variant="standard"
            fullWidth
            {...params}
            disabled={disabled}
            helperText={(errorMessage || hasError) ?
              <p style={{ color: "red" }}>{errorMessage || "Invalid Date"} </p> : ""}
          />
        )}
        ampm={false}
        ampmInClock={false}
        inputFormat="DD/MM/YYYY HH:mm"
        onError={(reason) => {
          setHasError(!!reason);
        }}
      />
    </LocalizationProvider>
  );
};

type TimeProps = {
  label: string;
  value: any;
  onChange: any;
  disabled?: boolean;
  minTime?: any;
};

export const TimePickerComponent = (props: TimeProps) => {
  const { label, value, onChange, disabled, minTime } = props;
  const [hasError, setHasError] = useState(false);
  return (
    <LocalizationProvider dateAdapter={AdapterMoment}>
      <TimePicker
        label={label}
        value={value}
        minTime={minTime}
        ampm={false}
        disabled={disabled}
        inputFormat="HH:mm"
        ampmInClock={false}
        onChange={onChange}
        renderInput={(params) => (
          <TextField
            variant="standard"
            fullWidth
            {...params}
            helperText={hasError ? <p style={{ color: "red" }}>{"Invalid Time"}</p> : ""}
          />
        )}
        onError={(reason) => {
          console.log("TimePicker onError reason:", reason);
          setHasError(!!reason);
        }}
      />
    </LocalizationProvider>
  );
};
