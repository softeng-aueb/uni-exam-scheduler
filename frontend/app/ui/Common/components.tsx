import Switch from "@mui/material/Switch";
import TrashIcon from "@mui/icons-material/Delete";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import FabButton from "../Buttons/FabButton";

import { onDelete, onEdit, onToggleStatus } from "./cruds";

type EditProps = {
  row: any;
  setData: any;
  setModalOpen: () => void;
}

type SwitchProps = {
  row: any;
  apiFunc: any;
  frontendFunc: any;
  entityNameSingular: string;
  data: any;
  id?: string;
  disabled?: boolean;
}

type DeleteProps = {
  row: any;
  apiFunc: any;
  frontendFunc: any;
  entityNameSingular: string;
  data: any;
  disabled?: boolean;
}

const BtnDelete = (props: DeleteProps) => {
  const { row, apiFunc, frontendFunc, entityNameSingular, data, disabled } = props;
  const payload = {
    apiFunc,
    frontendFunc,
    entityNameSingular,
    data,
  };
  return (
    <FabButton
      label="Delete"
      onClick={(e: React.MouseEvent) => onDelete(row, payload)}
      disabled={disabled}
    >
      <TrashIcon/>
    </FabButton>
  );
};

const BtnEdit = (props: EditProps) => {
  const { row, setData, setModalOpen } = props;
  return (
    <FabButton label="Edit" onClick={() => onEdit(row, { setData, setModalOpen })}>
      <EditOutlinedIcon/>
    </FabButton>
  );
};

const BtnSwitch = (props: SwitchProps) => {
  const { row, apiFunc, frontendFunc, entityNameSingular, data, id, disabled } = props;
  const payload = {
    apiFunc,
    frontendFunc,
    entityNameSingular,
    data,
    type: "STATUS_CHANGE",
  };

  return (
    <Switch
      disabled={disabled || ["FAILED", "DELETED"].includes(row.status)}
      checked={row.status === "ACTIVE"}
      onChange={onToggleStatus(row, payload)}
      id={id}
    />
  );
};

export {
  BtnDelete,
  BtnEdit,
  BtnSwitch,
};
