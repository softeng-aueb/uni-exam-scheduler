// >>> TEMPLATE FOR CRUD
"use server";
import axios from 'axios';

const url = `${process.env.BACKEND_URL}/examination_periods`

const mockData = [
    {
      "classrooms": [
        {
          "building": "Central",
          "covidCapacity": 70,
          "examCapacity": 120,
          "floor": 1,
          "generalCapacity": 200,
          "id": 2001,
          "maxNumSupervisors": 5,
          "name": "A21"
        },
        {
          "building": "Second",
          "covidCapacity": 25,
          "examCapacity": 40,
          "floor": 5,
          "generalCapacity": 70,
          "id": 2002,
          "maxNumSupervisors": 2,
          "name": "E2"
        }
      ],
      "course": {
        "courseCode": "CS105",
        "department": {
          "id": 3001,
          "name": "CS"
        },
        "id": 4001,
        "title": "Intro to Programming"
      },
      "date": "2023-01-03",
      "declaration": 400,
      "endTime": "13:30:00",
      "estimatedAttendance": 320,
      "estimatedSupervisors": 10,
      "examinationPeriod": {
        "academicYear": {
          "id": 1002,
          "isActive": false,
          "name": "2022-2023",
          "previousYear": {
            "id": 1001,
            "isActive": false,
            "name": "2021-2022"
          }
        },
        "id": 5001,
        "period": "WINTER",
        "startDate": "2023-01-01"
      },
      "id": 8001,
      "maxSupervisors": 7,
      "startTime": "11:30:00",
      "supervisions": [
        {
          "id": 10001,
          "isLead": true,
          "isPresent": true,
          "supervisor": {
            "department": {
              "id": 3001,
              "name": "CS"
            },
            "email": "jd@email.com",
            "id": 7001,
            "name": "John",
            "supervisor": "Dr. K",
            "supervisorCategory": "EDIP",
            "surname": "Doe",
            "telephone": "123456789"
          }
        }
      ]
    },
    {
      "classrooms": [
        {
          "building": "Third",
          "covidCapacity": 20,
          "examCapacity": 30,
          "floor": 3,
          "generalCapacity": 50,
          "id": 2004,
          "maxNumSupervisors": 1,
          "name": "C46"
        },
        {
          "building": "Central",
          "covidCapacity": 30,
          "examCapacity": 65,
          "floor": 2,
          "generalCapacity": 115,
          "id": 2003,
          "maxNumSupervisors": 3,
          "name": "B3"
        }
      ],
      "course": {
        "courseCode": "AF101",
        "department": {
          "id": 3004,
          "name": "Accounting"
        },
        "id": 4002,
        "title": "Accounting and Finance 101"
      },
      "date": "2023-01-05",
      "declaration": 450,
      "endTime": "14:30:00",
      "estimatedAttendance": 360,
      "estimatedSupervisors": 12,
      "examinationPeriod": {
        "academicYear": {
          "id": 1002,
          "isActive": false,
          "name": "2022-2023",
          "previousYear": {
            "id": 1001,
            "isActive": false,
            "name": "2021-2022"
          }
        },
        "id": 5001,
        "period": "WINTER",
        "startDate": "2023-01-01"
      },
      "id": 8002,
      "maxSupervisors": 4,
      "startTime": "12:30:00",
      "supervisions": []
    },
    {
      "classrooms": [],
      "course": {
        "courseCode": "CS105",
        "department": {
          "id": 3001,
          "name": "CS"
        },
        "id": 4001,
        "title": "Intro to Programming"
      },
      "date": "2024-06-10",
      "declaration": 400,
      "endTime": "12:30:00",
      "estimatedAttendance": 320,
      "estimatedSupervisors": 10,
      "examinationPeriod": {
        "academicYear": {
          "id": 1002,
          "isActive": false,
          "name": "2022-2023",
          "previousYear": {
            "id": 1001,
            "isActive": false,
            "name": "2021-2022"
          }
        },
        "id": 5001,
        "period": "WINTER",
        "startDate": "2023-01-01"
      },
      "id": 8007,
      "maxSupervisors": 0,
      "startTime": "18:30:00",
      "supervisions": [
        {
          "id": 10003,
          "isLead": false,
          "isPresent": false,
          "supervisor": {
            "department": {
              "id": 3003,
              "name": "Economics"
            },
            "email": "gb@email.com",
            "id": 7004,
            "name": "George",
            "supervisor": "Dr. E",
            "supervisorCategory": "EDIP",
            "surname": "Black",
            "telephone": "123456789"
          }
        }
      ]
    }
  ];

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
    console.log("===== Created Resp === :", examPeriod)
    return examPeriod;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readExaminationPeriodById(id: any): Promise<any> {
  const examUrl = `${process.env.BACKEND_URL}/examinations?examinationPeriod=${id}`
  try {
    const { data: examPeriod } = await axios.get(examUrl);
    console.log("===== GET Resp === :", examPeriod)
    return examPeriod;
  } catch (e: any) {
    console.log("===EEEEEE === :", e)
    return { status: "Failed" }
  }
}

export async function solveExaminationPeriod(examPeriodId: any): Promise<any> {

  try {
    const solveUrl = `${process.env.BACKEND_URL}/solve/${examPeriodId}`
    console.log("==== Exam ID === :", examPeriodId)
    const { data: examPeriod } = await axios.post(solveUrl);
    console.log("===== solveExaminationPeriod Resp === :", examPeriod)
    return examPeriod;
  } catch (e: any) {
    return { status: "Failed" }
  }
}


