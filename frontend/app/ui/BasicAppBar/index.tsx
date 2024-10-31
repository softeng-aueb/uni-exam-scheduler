import * as React from 'react';
import { signOut, useSession } from "next-auth/react";

import AdbIcon from '@mui/icons-material/Adb';
import AppBar from '@mui/material/AppBar';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Divider from "@mui/material/Divider";
import IconButton from '@mui/material/IconButton';
import Logout from "@mui/icons-material/Logout";
import Menu from "@mui/material/Menu";
import MenuIcon from '@mui/icons-material/Menu';
import MenuItem from "@mui/material/MenuItem";
import Toolbar from '@mui/material/Toolbar';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';

export default function BasicAppBar() {
  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);

  const { data: session }: any = useSession();

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const onLogout = async () => {
    await signOut({ callbackUrl: `${process.env.NEXTAUTH_URL}/login` });
    localStorage.clear();
  };

  const user_avatar = session?.user?.image ? session?.user?.image : "/images/avatar/img_avatar.png";


  return (<AppBar color="primary" position="static" sx={{ marginBottom: 4 }}>
    <Container maxWidth="xl">
      <Toolbar disableGutters>
        <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }}/>
        <Typography
          component="a"
          href="/"
          noWrap
          variant="h6"
          sx={{
            mr: 2,
            display: { xs: 'none', md: 'flex' },
            fontFamily: 'monospace',
            fontWeight: 700,
            letterSpacing: '.3rem',
            color: 'inherit',
            textDecoration: 'none',
          }}
        >
          ESAP
        </Typography>
        <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
          <IconButton
            size="large"
            aria-label="menu-appbar"
            aria-controls="menu-appbar"
            aria-haspopup="true"
            onClick={handleOpenNavMenu}
            color="inherit"
          >
            <MenuIcon/>
          </IconButton>
          <Menu
            id="menu-appbar"
            anchorEl={anchorElNav}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
            keepMounted
            transformOrigin={{ vertical: 'top', horizontal: 'left' }}
            open={Boolean(anchorElNav)}
            onClose={handleCloseNavMenu}
            sx={{ display: { xs: 'block', md: 'none' } }}
          >
            <MenuItem href="/admin" onClick={handleCloseNavMenu}>
              <Typography textAlign="center">Admin</Typography>
            </MenuItem>
            <MenuItem href="/calendar-events" onClick={handleCloseNavMenu}>
              <Typography textAlign="center">Calendar</Typography>
            </MenuItem>
            <MenuItem href="/upload" onClick={handleCloseNavMenu}>
              <Typography textAlign="center">Uploads</Typography>
            </MenuItem>
          </Menu>
        </Box>
        <AdbIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }}/>
        <Typography
          variant="h5"
          noWrap
          component="a"
          href="#app-bar-with-responsive-menu"
          sx={{
            mr: 2,
            display: { xs: 'flex', md: 'none' },
            flexGrow: 1,
            fontFamily: 'monospace',
            fontWeight: 700,
            letterSpacing: '.3rem',
            color: 'inherit',
            textDecoration: 'none',
          }}
        >
          LOGO
        </Typography>
        <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
          <Button href="/admin" onClick={handleCloseNavMenu} sx={{ my: 2, color: 'white', display: 'block' }}>
            Admin
          </Button>
          <Button href="/calendar-events" onClick={handleCloseNavMenu} sx={{ my: 2, color: 'white', display: 'block' }}>
            Calendar
          </Button>
          <Button href="/upload" onClick={handleCloseNavMenu} sx={{ my: 2, color: 'white', display: 'block' }}>
            Uploads
          </Button>
        </Box>
        <Box sx={{ flexGrow: 0 }}>
          <Tooltip title="Open settings">
            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar src={user_avatar} alt="Avatar"/>
            </IconButton>
          </Tooltip>
          <Menu
            sx={{ mt: '45px' }}
            id="menu-appbar"
            anchorEl={anchorElUser}
            anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            keepMounted
            transformOrigin={{ vertical: 'top', horizontal: 'right' }}
            open={Boolean(anchorElUser)}
            onClose={handleCloseUserMenu}
          >
            <MenuItem>
              <strong>{session?.user?.email}</strong>
            </MenuItem>
            <Divider/>
            <MenuItem onClick={onLogout}>
              <Logout sx={{ marginRight: "1rem" }}/>
              Logout
            </MenuItem>
          </Menu>
        </Box>
      </Toolbar>
    </Container>
  </AppBar>);
}
