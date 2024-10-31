import { errorAlert, infoAlert, successAlert, warningAlert } from "../../lib/helpers/swalGenerator";
import { v4 as uuid } from "uuid";

// On Create / Update
const handleContinue = async (values: any, payload: any, id: any) => {
  const { data, frontendFunc, apiFuncCreate, apiFuncUpdate, handleClose } = payload;
  try {
    const { data: resp } = id ? await apiFuncUpdate(id, values) : await apiFuncCreate(values);
    if (resp?.insertedId) {
      values._id = resp?.insertedId;
    } else if (resp?._id) {
      values = resp;
    } else if (id) {
      values._id = id;
    } else {
      values._id = uuid();
    }
    onAction(values, { data, frontendFunc });
    handleClose();
  } catch (e: any) {
    console.log(e);
  }
};

const onAction = (values: any, payload: any) => {
  const { data, frontendFunc } = payload;
  const isUpdate = data.some((d: any) => (d.hasOwnProperty("_id") ? d._id === values._id : d.hasOwnProperty("id") ? d.id === values.id : false));
  if (isUpdate) {
    const updated = data.map((d: any) => d._id === values._id ? { ...d, ...values } : d);
    frontendFunc(updated);
  } else {
    frontendFunc([...data, values]);
  }
};

const onDelete = async (row: any, payload: any) => {
  const { apiFunc, frontendFunc, entityNameSingular, data } = payload;

  const value = await warningAlert(`Are you sure want to DELETE this ${entityNameSingular}?`);
  if (value) {
    try {
      await apiFunc(row._id);
      frontendFunc(data.filter((val: any) => val._id !== row._id));
      await successAlert("Deleted", `${entityNameSingular} has been deleted successfully!`);
    } catch (ex: any) {
      if (ex.response && ex.response.status === 404) {
        return infoAlert("Not Found!", `${entityNameSingular} not found`);
      }
      await errorAlert();
    }
  }
};

const onEdit = (row: any, payload: any) => {
  const { setData, setModalOpen, isCopy } = payload;

  if (isCopy) {
    setData({ ...row, isCopy });
  } else {
    setData(row);
  }

  setModalOpen(true);
};

const onSearch = async (payload: any) => {
  const { query, data, frontendFunc, apiFunc } = payload;
  try {
    if (!query) {
      frontendFunc(data);
    } else {
      const { data: filtered } = await apiFunc(query);
      frontendFunc(filtered);
    }
  } catch (err: any) {
    console.log(err);
  }
};

const onToggleStatus = (row: any, payload: any) => async (event: any, item: any) => {
  const { apiFunc, frontendFunc, entityNameSingular, data, type } = payload;
  const status = item ? "ACTIVE" : "INACTIVE";
  const value = await warningAlert(`Are you sure you want to set as ${status} this ${entityNameSingular}?`);
  if (value) {
    try {
      // const res = await apiFunc(row._id, { status, type });
      const res = await apiFunc(row._id, { status });
      if (res.status === "SUCCESS" || res.data?.acknowledged) {
        frontendFunc(data.map((p: any) => (p._id === row._id ? { ...p, status } : p)));
      }
      await successAlert("Success", `Status of this ${entityNameSingular} successfully changed`);
    } catch (ex: any) {
      if (ex.response && ex.response.status === 404) {
        return infoAlert("Not Found!", `${entityNameSingular} not found`);
      }
      await errorAlert();
    }
  }
};

export {
  handleContinue,
  onAction,
  onDelete,
  onEdit,
  onSearch,
  onToggleStatus,
};
