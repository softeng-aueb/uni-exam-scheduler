import * as React from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";

type SelectProps = {
  handleChange: any;
  label: string;
  name: string;
  value: string;
  data: any;
  hasError?: any;
  id?: string;
}
export default function SelectMenu({
                                     handleChange,
                                     label,
                                     name,
                                     value,
                                     data,
                                     hasError,
                                     id,
                                   }: SelectProps) {
  return (
    <FormControl variant="standard" sx={{ m: 1, width: "100%" }}>
      <InputLabel
        id="demo-simple-select-standard-label"
        required
        error={hasError}
      >
        {label}
      </InputLabel>
      <Select
        labelId={`label-${label}`}
        name={name}
        id={id || `label-${label}`}
        value={value}
        onChange={handleChange}
        label={label}
        error={hasError}
      >
        {data && data.map((menu) => (
          <MenuItem value={menu.value} key={menu.value} sx={{ display: menu.display }}>
            {menu.label}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}
