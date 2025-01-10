import Stack from "@mui/material/Stack";
import Chip from "@mui/material/Chip";

export default function Tags({ data, handleDelete }) {
  return (

    <Stack direction='row' gap={1}>
      <Chip label={data} variant="outlined" onDelete={() => handleDelete(data)}/>
    </Stack>

  );
};
