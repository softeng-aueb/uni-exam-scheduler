
import { styled } from '@mui/material/styles';
import Typography from '@mui/material/Typography';

// Define styled Typography for different custom styles
const CustomTitle = styled(Typography)({
  color: '#078D95',
  fontSize: '20px',
  lineHeight: '28px',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
});

const CustomSubtitle = styled(Typography)({
  color: '#9B9B9B',
  fontSize: '16px',
  lineHeight: '20px',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
});

const CustomSubtitleLarge = styled(Typography)({
  color: '#9B9B9B',
  fontSize: '22px',
  lineHeight: '32px',
  fontWeight: 'bold',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
});

const CustomSubtitleSmall = styled(Typography)({
  color: '#9B9B9B',
  fontSize: '12px',
  lineHeight: '22px',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
});

const CustomFigureKey = styled(Typography)({
  color: '#9B9B9B',
  fontSize: '14px',
  lineHeight: '18px',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
});

const CustomFigureValue = styled(Typography)({
  color: '#30CA3F',
  fontSize: '14px',
  lineHeight: '18px',
  marginLeft: '0.5rem',
});

export {
  CustomTitle,
  CustomSubtitle,
  CustomSubtitleLarge,
  CustomSubtitleSmall,
  CustomFigureKey,
  CustomFigureValue,
};
