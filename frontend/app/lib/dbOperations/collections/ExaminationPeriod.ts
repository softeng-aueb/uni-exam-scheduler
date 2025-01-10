// >>> TEMPLATE FOR CRUD
"use server";
import axios from 'axios';

const url = `${process.env.BACKEND_URL}/examination_periods`

export async function createExaminationPeriod(data: any): Promise<any> {
  try {
    const { data: createdResp } = await axios.post(url, data);
    return createdResp;

  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readExaminationPeriod(id: any): Promise<any> {

  try {
    const { data: examPeriod } = await axios.get(`${url}?academicYearId=${id}`);
    return examPeriod;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readExaminationPeriodById(id: any): Promise<any> {
  const examUrl = `${process.env.BACKEND_URL}/examinations?examinationPeriod=${id}`
  try {
    const { data: examPeriod } = await axios.get(examUrl);
    return examPeriod;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function solveExaminationPeriod(examPeriodId: any): Promise<any> {

  try {
    const solveUrl = `${process.env.BACKEND_URL}/solve/${examPeriodId}`
    const { data: examPeriod } = await axios.get(solveUrl);
    return examPeriod;
  } catch (e: any) {
    return { status: "Failed" }
  }
}


