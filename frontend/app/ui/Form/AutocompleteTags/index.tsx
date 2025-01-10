import React from "react";

// MUI
import Autocomplete from "@mui/material/Autocomplete";
import Chip from "@mui/material/Chip";
import TextField from "@mui/material/TextField";

// Props Type
type Props = {
  options: Array<string>;
  onChange: any;
  label: string;
  multiple?: boolean;
  value: Array<string>;
  id?: string;
  hasError?: boolean;
  errorMessage?: string;
}

export default function AutocompleteTags(props: Props) {
  const { options, onChange, label, multiple = true, value, id, hasError, errorMessage } = props;
  const formattedOptions = options.map((tag) => tag);
  const Id = label.split(" ").shift().toLowerCase() + "-tags-filled";

  return (
    <Autocomplete
      multiple={multiple}
      onChange={onChange}
      id={id ? id : Id}
      options={formattedOptions}
      // defaultValue={[]}
      value={value || []}
      renderTags={(value: readonly string[], getTagProps) =>
        value?.map((option: string, index: number) => (
          <Chip
            key={index}
            variant="outlined"
            label={option}
            id={id}
            // {...getTagProps({ index })} // TODO: This one causes "Warning: A props object containing a "key" prop is being spread into JSX:"
            size="small"
          />
        ))
      }
      renderInput={(params) => (
        <TextField
          {...params}
          variant="standard"
          label={label}
          placeholder={label}
          error={hasError}
          helperText={errorMessage}
        />
      )}
    />
  );
}
