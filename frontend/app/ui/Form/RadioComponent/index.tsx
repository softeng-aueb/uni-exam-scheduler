import * as React from "react";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";

type RadioProps = {
  label: string;
  value: string;
  onTypeSelect: any;
  options: any;
  id?: string;
}
export default function RadioComponent({
                                         label,
                                         value,
                                         onTypeSelect,
                                         options,
                                         id,
                                       }: RadioProps) {
  return (
    <FormControl>
      {label && (
        <FormLabel id="demo-row-radio-buttons-group-label">{label}</FormLabel>
      )}
      <RadioGroup
        row
        aria-labelledby="demo-row-radio-buttons-group-label"
        name="row-radio-buttons-group"
        value={value}
        onChange={onTypeSelect}
        id={id || "row-radio-buttons-group"}
      >
        {options.map(({ label, value }) => (
          <FormControlLabel
            value={value}
            control={<Radio/>}
            label={label}
            key={value}
          />
        ))}
      </RadioGroup>
    </FormControl>
  );
}
