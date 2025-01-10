import CircularProgress from "@mui/material/CircularProgress";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";

export default function SimpleLoader({ message = "Loading...", centered = false, withPadding = false }) {
  return (
    <Grid
      container
      spacing={1}
      alignItems="center"
      justifyContent={centered ? "center" : "left"}
      p={withPadding ? 2 : 0}
    >
      <Grid item>
        <Box sx={{ display: "flex" }}><CircularProgress/></Box>
      </Grid>
      <Grid item>
        <Typography variant="h6">{message}</Typography>
      </Grid>
    </Grid>
  );
}
