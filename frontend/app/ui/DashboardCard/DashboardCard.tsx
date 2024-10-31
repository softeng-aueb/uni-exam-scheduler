import * as React from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";

// MUI
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";

// Recharts
import {
  Area,
  Bar,
  BarChart,
  CartesianGrid,
  ComposedChart,
  Legend,
  Line,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

// Interface
import { IDashboardCardsProps } from "../../lib/dbOperations/schemas/ComponentsInterfaces";
import IconBox from "../IconBox";
import { CustomFigureKey, CustomFigureValue, CustomSubtitle, CustomSubtitleLarge, CustomSubtitleSmall, CustomTitle } from "../TypographyBox";

export default function DashboardCard(props: IDashboardCardsProps) {
  const {
    icon,
    title,
    titleLink,
    subtitle,
    subtitleLarge,
    subtitleSmall,
    figures,
    element,
    barData,
    lineData,
    children,
  } = props;

  const router: any = useRouter();
  return (
    <Card sx={{ width: "100%", marginBottom: 4 }}>
      <CardContent sx={{ padding: "25px 20px !important" }}>
        <Box sx={{ display: "flex" }}>

          {/* Icon */}
          {icon && <IconBox icon={icon}/> }

          <Box sx={{ display: 'flex', flexDirection: 'column', marginLeft: '1rem', width: '80%' }}>
      {/* Title with titleLink */}
      {title && titleLink && (
        <Link href={titleLink} passHref legacyBehavior>
          <a style={{ cursor: 'pointer', textDecoration: 'none' }}>
            <CustomTitle noWrap onClick={() => router.push(titleLink)}>
              {title}
            </CustomTitle>
          </a>
        </Link>
      )}

      {/* Title without titleLink */}
      {title && !titleLink && (
        <CustomTitle noWrap>
          {title}
        </CustomTitle>
      )}

      {/* Subtitle */}
      {subtitle && (
        <CustomSubtitle noWrap>
          {subtitle}
        </CustomSubtitle>
      )}

      {/* Subtitle Large */}
      {subtitleLarge && (
        <CustomSubtitleLarge noWrap>
          {subtitleLarge}
        </CustomSubtitleLarge>
      )}

      {/* Subtitle Small */}
      {subtitleSmall && (
        <CustomSubtitleSmall noWrap>
          {subtitleSmall}
        </CustomSubtitleSmall>
      )}

      {/* Figures */}
      {figures &&
        figures.map(({ key, val }, index) => (
          <Box key={index} sx={{ display: 'flex', width: '100%' }}>
            <CustomFigureKey noWrap>
              {key}
            </CustomFigureKey>
            <CustomFigureValue>
              {val}
            </CustomFigureValue>
          </Box>
        ))}

      {/* Element */}
      {element}
    </Box>

        </Box>

        {/* Bar */}
        {barData && (
          <Box sx={{ marginTop: 1 }}>
            <ResponsiveContainer width="100%" height={205}>
              <BarChart data={barData} margin={{ left: 30, right: 30 }}>
                <XAxis dataKey="name" scale="point" axisLine={false} tickLine={false}/>
                <Tooltip contentStyle={{ backgroundColor: "rgba(255, 255, 255, .8)" }}/>
                <Bar dataKey="pv" fill="#8884d8" radius={5}/>
              </BarChart>
            </ResponsiveContainer>
          </Box>
        )}

        {/* Line */}
        {lineData && (
          <Box sx={{ marginTop: 1 }}>
            <ResponsiveContainer width="100%" height={205}>
              <ComposedChart data={lineData}>
                <CartesianGrid stroke="#f5f5f5"/>
                <XAxis dataKey="day" axisLine={false} tickLine={false}/>
                <YAxis axisLine={false} tickLine={false} orientation="right"/>
                <Tooltip contentStyle={{ backgroundColor: "rgba(255, 255, 255, .8)" }}/>
                <Legend
                  layout="horizontal"
                  verticalAlign="top"
                  align="right"
                  iconType="plainline"
                  wrapperStyle={{ top: -10 }}
                />
                <Area
                  type="monotone"
                  dataKey="Today"
                  fill="#e1fcfe"
                  stroke="#078d95"
                  strokeWidth={2}
                />
                <Line
                  type="monotone"
                  dataKey="Yesterday"
                  stroke="#DFE0EB"
                  strokeWidth={2}
                />
              </ComposedChart>
            </ResponsiveContainer>
          </Box>
        )}

        {children && (
          <Box sx={{ marginTop: 1 }}>
            {children}
          </Box>
        )}

      </CardContent>
    </Card>
  );
}
