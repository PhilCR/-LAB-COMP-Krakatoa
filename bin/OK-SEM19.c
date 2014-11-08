#include <malloc.h>
#include <stdlib.h>
#include <stdio.h>

typedef int boolean;
#define true 1
#define false 0

typedef
   void (*Func)();

typedef
   struct _St_A{
      Func *vt;
      int _A_anInt;
      _class_A* _A_anA;
   } _class_A;

_class_A *new_A(void);

int _static_A_staticAnInt;
_class_A* _static_A_staticAnA;


int _A_getAnInt( _class_A *this){

   return this->_anInt;

}

void _A_setAnInt( _class_A *this, int _anInt){
   this->_anInt = _anInt;

}

_class_A* _A_getAnA( _class_A *this){

   return this->_anA;

}

void _A_setAnA( _class_A *this, _class_A* _anA){
   this->_anA = _anA;

}

void _A_method( _class_A *this){
   this->_anInt = 5;
   this->_anA = new_A();
   this.anA.setAnInt(6);
   write(this.anA.getAnInt());
   _class_A *_b;
   _b = this.anA.getAnA();
   write(( (int (*)(_class_A *) _b->vt[0])(_b) ));

}

void _static_A_staticMethod(){
   _class_A *_a;
   _a = new_A();
   _class_A *_b;
   _b = new_A();
   ( (void (*)(_class_A *, int) _b->vt[1])(_b, 0) );
   ( (void (*)(_class_A *, _class_A*) _a->vt[3])(_a, _b) );
   _static_A_staticAnInt = ( (int (*)(_class_A *) _a->vt[0])(_a) );
   _static_A_staticAnA = ( (_class_A* (*)(_class_A *) _a->vt[2])(_a) );
   read(_static_A_staticAnInt);

   A.staticAnA.setAnInt(1);
   write(A.staticAnA.getAnInt());
   ( (void (*)(_class_A *, int) _a->vt[1])(_a, 2) );
   _static_A_staticAnInt = (_static_A_staticAnInt) + 1;

}

Func VTclass_A[] = {
   ( void (*)() ) _A_getAnInt,
   ( void (*)() ) _A_setAnInt,
   ( void (*)() ) _A_getAnA,
   ( void (*)() ) _A_setAnA,
   ( void (*)() ) _A_method
};

_class_A *new_A(){
   _class_A *t;

   if( (t = malloc(sizeof(_class_A))) != NULL )
      t->vt = VTclass_A;
   return t;
}

typedef
   struct _St_B{
      Func *vt;
      int _A_anInt;
      _class_A* _A_anA;
      _class_A* _B_anAB;
   } _class_B;

_class_B *new_B(void);

int _static_B_staticAnInt;


void _B_method( _class_B *this){
   this->_anAB = new_A();
   this.anAB.setAnInt(11);

}

void _static_B_staticMethod(){
   _static_B_staticAnInt = 10;

}

int _static_B_getAnIntB(){

   return _static_B_staticAnInt;

}

Func VTclass_B[] = {
   ( void (*)() ) _A_getAnInt,
   ( void (*)() ) _A_setAnInt,
   ( void (*)() ) _A_getAnA,
   ( void (*)() ) _A_setAnA,
   ( void (*)() ) _B_method
};

_class_B *new_B(){
   _class_B *t;

   if( (t = malloc(sizeof(_class_B))) != NULL )
      t->vt = VTclass_B;
   return t;
}

typedef
   struct _St_Program{
      Func *vt;
   } _class_Program;

_class_Program *new_Program(void);



void _Program_run( _class_Program *this){
   _class_A *_a;
   _a = new_A();
   ( (void (*)(_class_A *) _a->vt[4])(_a) );
   _static_A_staticMethod();
   _class_B *_b;
   _b = new_B();
   _static_B_staticMethod();
   ( (void (*)(_class_B *) _b->vt[4])(_b) );

}

Func VTclass_Program[] = {
   ( void (*)() ) _Program_run
};

_class_Program *new_Program(){
   _class_Program *t;

   if( (t = malloc(sizeof(_class_Program))) != NULL )
      t->vt = VTclass_Program;
   return t;
}

int main() {
   _class_Program *program;
   program = new_Program();
   ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
   return 0;
}
