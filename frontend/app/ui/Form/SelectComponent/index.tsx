import MenuItem from "@mui/material/MenuItem";
import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import FormHelperText from "@mui/material/FormHelperText";
import InputLabel from "@mui/material/InputLabel";
import { isEmpty } from "lodash";

interface Props {
  containerStyle?: Object;
  value: string | null;
  onSelect: Function;
  menuItems: Array<any>;
  hasError?: boolean;
  errorMessage?: string;
  placeHolder?: string;
  size?: "medium" | "small";
  id?: string;
  onClick?: Function;
  inputRequired?: boolean
  disabled?: boolean
  multiple?: boolean
  readOnly?: boolean
}

export default function SelectComponent(props: Props) {
  /**
   *   menu items in the following format
   * [{label:"", value:""}]
   */
  const {
    containerStyle = {},
    value,
    onSelect,
    menuItems,
    hasError = false,
    errorMessage = "",
    placeHolder = "",
    size = "medium",
    id,
    onClick,
    inputRequired = false,
    disabled = false,
    multiple = false,
    readOnly = false,
  } = props;

  return (
    <Box sx={{ ...containerStyle, minWidth: 120 }}>
      <FormControl fullWidth size={size} variant="standard">
        {!isEmpty(placeHolder) && (
          <InputLabel
            htmlFor="outlined-adornment-email"
            required={inputRequired}
            error={hasError}
          >
            {placeHolder}
          </InputLabel>
        )}
        <Select
          disabled={disabled}
          defaultValue={""}
          labelId={id ? `${id}-label` : "simple-select-label"}
          id={id || "simple-select"}
          value={value}
          label={!isEmpty(placeHolder) ? placeHolder : undefined}
          onChange={(e) => onSelect(e)}
          error={hasError}
          onClick={(e: any) => onClick && onClick(e)}
          multiple={multiple}
          readOnly={readOnly}
        >
          {menuItems?.length > 0 &&
            menuItems?.map((menu, idx) => (
              <MenuItem value={menu.value} key={`${idx}-${menu.value}`}>
                {menu.label}
              </MenuItem>
            ))}
        </Select>
        <FormHelperText
          style={{
            display: hasError ? "block" : "none",
            color: "red",
            fontSize: 10,
          }}
          id={"component-error-email"}
        >
          {errorMessage}
        </FormHelperText>
      </FormControl>
    </Box>
  );
}
