// SortMain.cpp : Definiuje eksportowane funkcje dla aplikacji DLL.
//

#include "stdafx.h"
#include <iostream>
#include <stdlib.h>
#include <jni.h>
#include "com_company_SortMain.h"

JNIEXPORT jobjectArray JNICALL Java_com_company_SortMain_sort01(JNIEnv * env, jobject obj, jobjectArray arr, jobject order);
JNIEXPORT jobjectArray JNICALL Java_com_company_SortMain_sort02(JNIEnv *, jobject, jobjectArray);
JNIEXPORT void JNICALL Java_com_company_SortMain_sort03(JNIEnv *, jobject);
int asc(const void * a, const void * b);
int desc(const void * a, const void * b);

	/*
	 * Class:     com_company_SortMain
	 * Method:    sort01
	 * Signature: ([Ljava/lang/Double;Ljava/lang/Boolean;)[Ljava/lang/Double;
	 */

	 // zak�adamy, �e po stronie kodu natywnego b�dzie sortowana przekazana tablica a 
	 // (order=true oznacza rosn�co, order=false oznacza malej�co)
	 // metoda powinna zwr�ci� posortowan� tablic�

JNIEXPORT jobjectArray JNICALL Java_com_company_SortMain_sort01(JNIEnv * env, jobject obj, jobjectArray arr, jobject order) {

	//double
	jobject doubleObject = env->GetObjectArrayElement(arr, 0);
	unsigned int length = env->GetArrayLength(arr);
	jclass doubleClass = env->GetObjectClass(doubleObject);
	jmethodID doubleValue = env->GetMethodID(doubleClass, "doubleValue", "()D");

	//wartosci w tablicy
	double *arrCpp = new double[length];
	for (unsigned int i = 0; i < length; i++) {
		doubleObject = env->GetObjectArrayElement(arr, i);
		arrCpp[i] = env->CallDoubleMethod(doubleObject, doubleValue);
	}

	//order
	jclass booleanClass = env->GetObjectClass(order);
	jmethodID booleanValue = env->GetMethodID(booleanClass, "booleanValue", "()Z");

	//wartosc order
	bool orderCpp = env->CallBooleanMethod(order, booleanValue);

	//sortowanie
	if (orderCpp) qsort(arrCpp, length, sizeof(double), asc);
	else qsort(arrCpp, length, sizeof(double), desc);

	//tablica Double (obiektow Java)
	jobjectArray result = env->NewObjectArray(length, doubleClass, NULL);
	jmethodID constructor = env->GetMethodID(doubleClass, "<init>", "(D)V");
	for (unsigned int i = 0; i < length; i++) {
		env->SetObjectArrayElement(result, i, env->NewObject(doubleClass, constructor, arrCpp[i]));
	}

	//usuwanie zbednych elementow
	delete[] arrCpp;
	env->DeleteLocalRef(doubleClass);
	env->DeleteLocalRef(doubleObject);

	return result;
}
	/*
	 * Class:     com_company_SortMain
	 * Method:    sort02
	 * Signature: ([Ljava/lang/Double;)[Ljava/lang/Double;
	 */

	 // zak�adamy, �e drugi atrybut b�dzie pobrany z obiektu przekazanego do metody natywnej (czyli b�dzie brana warto�� pole order) 

JNIEXPORT jobjectArray JNICALL Java_com_company_SortMain_sort02(JNIEnv * env, jobject obj, jobjectArray arr){
	
	//double
	jobject doubleObject = env->GetObjectArrayElement(arr, 0);
	unsigned int length = env->GetArrayLength(arr);
	jclass doubleClass = env->GetObjectClass(doubleObject);
	jmethodID doubleValue = env->GetMethodID(doubleClass, "doubleValue", "()D");

	//wartosci w tablicy
	double *arrCpp = new double[length];
	for (unsigned int i = 0; i < length; i++) {
		doubleObject = env->GetObjectArrayElement(arr, i);
		arrCpp[i] = env->CallDoubleMethod(doubleObject, doubleValue);
	}

	//order
	jclass thisClass = env->GetObjectClass(obj);
	jfieldID fieldID = env->GetFieldID(thisClass, "order", "Ljava/lang/Boolean;");
	jobject order = env->GetObjectField(obj, fieldID);
	jclass booleanClass = env->GetObjectClass(order);
	jmethodID booleanValue = env->GetMethodID(booleanClass, "booleanValue", "()Z");

	//wartosc order
	bool orderCpp = env->CallBooleanMethod(order, booleanValue);

	//sortowanie
	if (orderCpp) qsort(arrCpp, length, sizeof(double), asc);
	else qsort(arrCpp, length, sizeof(double), desc);
	
	//tablica Double (obiektow Java)
	jobjectArray result = env->NewObjectArray(length, doubleClass, NULL);
	jmethodID constructor = env->GetMethodID(doubleClass, "<init>", "(D)V");
	for (unsigned int i = 0; i < length; i++) {
		env->SetObjectArrayElement(result, i, env->NewObject(doubleClass, constructor, arrCpp[i]));
	}

	//usuwanie zbednych elementow
	delete[] arrCpp;
	env->DeleteLocalRef(doubleClass);
	env->DeleteLocalRef(doubleObject);

	return result;
}

	/*
	 * Class:     com_company_SortMain
	 * Method:    sort03
	 * Signature: ()V
	 */

	 // zak�adamy, �e po stronie natywnej utworzone zostanie okienko pozwalaj�ce zdefiniowa� zawarto�� tablicy do sortowania 
	 // oraz warunek okre�laj�cy spos�b sortowania order.
	 // wczytana tablica powinna zosta� przekazana do obiektu Javy na pole a, za� warunek sortowania powinien zosta� przekazany
	 // do pola orded
	 // Wynik sortowania (tablica b w obiekcie Java) powinna wylicza� metoda Javy multi04 
	 // (korzystaj�ca z parametr�w a i order, wstawiaj�ca wynik do b).

JNIEXPORT void JNICALL Java_com_company_SortMain_sort03(JNIEnv * env, jobject obj) {

	//wczytanie tablicy
	unsigned int length;
	std::cout << "Liczba elementow w tablicy: ";
	std::cin >> length;
	double* arrCpp = new double[length];

	for (unsigned int i = 0; i < length; i++){
		std::cout << i+1 << " element: ";
		std::cin >> arrCpp[i];
	}

	//wczytanie sposobu sortowania
	bool order;
	std::cout << "Sposob sortowania: \n1 - rosnaco \n0 - malejaco\n ";
	std::cin >> order;

	//Double (Java)
	jclass doubleClass = env->FindClass("java/lang/Double");
	jobjectArray arrJava = env->NewObjectArray(length, doubleClass, NULL);
	jmethodID constructor = env->GetMethodID(doubleClass, "<init>", "(D)V");

	//tablica objekt�w Double (Java)
	for (unsigned int i = 0; i < length; i++){
		jobject doubleObject = env->NewObject(doubleClass, constructor, arrCpp[i]);
		env->SetObjectArrayElement(arrJava, i, doubleObject);
	}

	//wpisanie tablicy do tablicy "a" w obiekcie
	jclass thisClass = env->GetObjectClass(obj);
	jfieldID fieldID = env->GetFieldID(thisClass, "a", "[Ljava/lang/Double;");
	env->SetObjectField(obj, fieldID, arrJava);

	//wpisanie sposobu sortowania do obiektu
	jclass booleanClass = env->FindClass("java/lang/Boolean");
	jmethodID booleanConstructor = env->GetMethodID(booleanClass, "<init>", "(Z)V");
	jfieldID orderFieldID = env->GetFieldID(thisClass, "order", "Ljava/lang/Boolean;");
	jobject orderObject = env->NewObject(booleanClass, booleanConstructor, order);
	env->SetObjectField(obj, orderFieldID, orderObject);

	//wywolanie metody sort04 obiektu
	jmethodID sort04 = env->GetMethodID(thisClass, "sort04", "()V");
	env->CallVoidMethod(obj, sort04);
	return;
}

int asc(const void * a, const void * b){	

	double num1 = *(double*)a;
	double num2 = *(double*)b;
	if (num1 < num2) return -1;
	else if (num1 > num2) return 1;
	else return 0;
}

int desc(const void * a, const void * b) {
	double num1 = *(double*)a;
	double num2 = *(double*)b;
	if (num1 > num2) return -1;
	else if (num1 < num2) return 1;
	else return 0;
}