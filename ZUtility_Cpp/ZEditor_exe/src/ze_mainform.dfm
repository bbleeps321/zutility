object ZEditorForm: TZEditorForm
  Left = 0
  Top = 0
  Caption = 'ZEditor'
  ClientHeight = 632
  ClientWidth = 919
  Color = clBlack
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWhite
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object TabPanel: TPanel
    Left = 0
    Top = 55
    Width = 919
    Height = 577
    Align = alBottom
    Color = clBlack
    ParentBackground = False
    TabOrder = 0
    ExplicitTop = 35
    object TabControl: TPageControl
      Left = 1
      Top = 1
      Width = 917
      Height = 575
      Align = alClient
      PopupMenu = TextFieldPopupMenu
      TabOrder = 0
    end
  end
  object MainMenu1: TMainMenu
    Left = 888
    Top = 65528
    object File1: TMenuItem
      Caption = 'File'
      object Open1: TMenuItem
        Caption = 'Open'
      end
      object Save1: TMenuItem
        Caption = 'Save'
      end
      object SaveAs1: TMenuItem
        Caption = 'Save As'
      end
      object Exit1: TMenuItem
        Caption = 'Exit'
      end
    end
    object Edit1: TMenuItem
      Caption = 'Edit'
      object Cut1: TMenuItem
        Caption = 'Cut'
      end
      object Copy1: TMenuItem
        Caption = 'Copy'
      end
      object Paste1: TMenuItem
        Caption = 'Paste'
      end
    end
    object Options1: TMenuItem
      Caption = 'Options'
    end
    object About1: TMenuItem
      Caption = 'About'
    end
  end
  object TextFieldPopupMenu: TPopupMenu
    Left = 16
    Top = 64
    object NewTab1: TMenuItem
      Caption = 'New Tab'
      ShortCut = 16468
      OnClick = NewTab1Click
    end
    object CloseThisTab1: TMenuItem
      Caption = 'Close This Tab'
      ShortCut = 16471
      OnClick = CloseThisTab1Click
    end
    object CloseAllOtherTabs1: TMenuItem
      Caption = 'Close All Other Tabs'
      ShortCut = 24663
      OnClick = CloseAllOtherTabs1Click
    end
  end
end
