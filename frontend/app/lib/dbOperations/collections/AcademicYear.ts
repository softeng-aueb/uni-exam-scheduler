// >>> TEMPLATE FOR CRUD
"use server";
import axios from 'axios';

const url = `${process.env.BACKEND_URL}/academic_years`

export async function createAcademicYear(data: any): Promise<any> {
  try {
    const { data: createdResp } = await axios.post(url, data);
    console.log("=== Created resp +++ == :", createdResp)
    return createdResp;

  } catch (e: any) {
    console.log("== Errorrrr == :", e)
    return { status: "Failed" }
  }
}

export async function readAcademicYear(id: any): Promise<any> {
  try {
    const { data: academicYear } = await axios.get(`${url}/${id}`);
    return academicYear;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readAcademicYears(): Promise<any> {
  try {
    const { data: academicYears } = await axios.get(url);
    return academicYears;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function updateAcademicYear(id: string, updateObj: any): Promise<any> {
  try {
    const { data: updatedResp } = await axios.put(`${url}/${id}`, updateObj);
    return updatedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}


